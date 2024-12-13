package com.zero.rainy.cache.lock;

import cn.hutool.core.util.RandomUtil;
import com.zero.rainy.cache.consts.Scripts;
import com.zero.rainy.core.lock.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Redis 分布式锁实现.
 *
 * @author Zero.
 * <p> Created on 2024/12/13 23:17 </p>
 */
@Slf4j
@SuppressWarnings("all")
public class RedisDistributedLock implements DistributedLock {
    private final ThreadLocal<String> TAGS = new ThreadLocal<>();
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisSerializer<String> keySerializer;
    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.keySerializer = redisTemplate.getStringSerializer();
    }

    @Override
    public boolean lock(String key, Duration expire, int retryTimes, Duration retryInterval) {
        boolean locked = upLock(key, expire);
        while (!locked && retryTimes-- > 0){
            try {
                log.debug("[RedisDistributedLock] Try again lock [{}], Remaining retries: {}", key, retryTimes);
                TimeUnit.MILLISECONDS.sleep(retryInterval.toMillis());
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
            locked = upLock(key, expire);
        }
        return locked;
    }

    /**
     * 上锁
     * @param key       锁标识
     * @param expire    锁有效时长
     * @return          是否上锁成功
     */
    private Boolean upLock(final String key, Duration expire){
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) conn -> {
                String tag = RandomUtil.randomString(16);
                String oldTag = TAGS.get();
                TAGS.set(tag);
                byte[] keyBytes = keySerializer.serialize(key);
                byte[] valueBytes = keySerializer.serialize(tag);
                Boolean locked = conn.commands().set(keyBytes, valueBytes, Expiration.from(expire), RedisStringCommands.SetOption.ifAbsent());
                if (!locked){
                    TAGS.set(oldTag);
                }
                return locked;
            });
        }catch (Exception e){
            // 加锁失败了
            log.error("[RedisDistributedLock] Locking [{}] failed", key, e);
            return Boolean.FALSE;
        }
    }

    @Override
    public boolean unlock(String key) {
        String tag = TAGS.get();
        if (StringUtils.isBlank(tag)) return false;
        try {
            return Boolean.TRUE.equals(redisTemplate.execute((RedisCallback<Boolean>) conn -> {
                byte[] scripts = keySerializer.serialize(Scripts.UNLOCK_SCRIPTS);
                byte[] keyBytes = keySerializer.serialize(key);
                byte[] valueBytes = keySerializer.serialize(tag);
                return conn.scriptingCommands().eval(scripts, ReturnType.BOOLEAN, 1, keyBytes, valueBytes);
            }));
        }catch (Exception e){
            log.error("[RedisDistributedLock] Unlocking [{}] failed", key, e);
        }finally {
            TAGS.remove();
        }
        return false;
    }
}
