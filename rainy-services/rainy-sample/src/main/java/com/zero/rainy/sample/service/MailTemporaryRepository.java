package com.zero.rainy.sample.service;

import com.zero.rainy.cache.template.CacheTemplate;
import com.zero.rainy.sample.model.MailMessage;
import com.zero.rainy.sample.model.MailStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;

/**
 * 邮箱临时存储库
 *
 * @author Zero.
 * <p> Created on 2024/12/5 10:54 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailTemporaryRepository {
    private final CacheTemplate cacheTemplate;
    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations<String, String, MailMessage> opsForHash(){
        return redisTemplate.opsForHash();
    };

    /**
     * 向邮箱存储库中添加一批邮件
     * @param key       邮箱key
     * @param store     邮件列表
     */
    public void putAll(String key, MailStorage store) {
        opsForHash().putAll(key, store);
        renewLease(key);
    }
    public void putAll(MailStorage store){
        this.putAll(store.getKey(), store);
    }

    public void renewLease(String key){
       redisTemplate.expire(key, expired());
    }

    /**
     * 添加单封邮件
     * @param key       邮箱Key
     * @param sender    发件人
     * @param message   邮件信息
     */
    public void put(String key, String sender, MailMessage message){
        opsForHash().put(key, sender, message);
        renewLease(key);
    }

    /**
     * 根据发件人获取邮箱中的某封邮件
     * @param key       邮箱Key
     * @param sender    发件人
     */
    public MailMessage get(String key, String sender){
        return opsForHash().get(key, sender);
    }

    /**
     * 查询某个邮箱库是否存在指定发件人的邮件
     * @param key       邮箱Key
     * @param sender    发件人
     */
    public boolean hasExists(String key, String sender){
        return opsForHash().hasKey(key, sender);
    }

    /**
     * 根据发件人，删除邮件
     * 
     * @param key       邮箱Key
     * @param keys      发件人
     */
    public void delete(String key, Object... keys){
        Long deleted = opsForHash().delete(key, keys);
    }

    /**
     * 删除邮箱存储库
     * @param keys  邮箱Keys
     */
//    public void delete(String keys){
//        redisTemplate.delete(keys);
//    }

    /**
     * 按照发件人获取存储库中所有的邮件，
     * @param key   邮箱Key
     */
    public Map<String, MailMessage> getAll(String key) {
        return opsForHash().entries(key);
    }

    /**
     * 存储库的有效期
     */
    public Duration expired() {
        return Duration.ofMinutes(30);
    }
}
