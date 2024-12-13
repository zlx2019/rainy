package com.zero.rainy.core.lock;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁顶级接口
 *
 * @author Zero.
 * <p> Created on 2024/12/13 22:47 </p>
 */
public interface DistributedLock {
    /**
     * 默认分布式锁有效时长(3s)
     */
    Duration DEFAULT_EXPIRE_TIME = Duration.ofSeconds(3);
    /**
     * 默认重试间隔时长(200ms)
     */
    Duration DEFAULT_RETRY_INTERVAL = Duration.ofMillis(200);
    /**
     * 失败后默认自旋次数(3)
     */
    int DEFAULT_RETRY_TIMES = 3;

    /**
     * 获取锁
     *
     * @param key           锁标识
     * @param expire        锁的有效时长
     * @param retryTimes    加锁失败后重试的次数
     * @param retryInterval 重试的间隔时长
     * @return              最终是否加锁成功
     */
    boolean lock(String key, Duration expire, int retryTimes, Duration retryInterval);

    /**
     * 尝试获取锁（非阻塞式，失败立即返回）
     * @param key   锁标识
     * @return      是否获取到锁
     */
    default boolean tryLock(String key){
        return tryLock(key, DEFAULT_EXPIRE_TIME);
    }


    default boolean lock(String key){
        return lock(key, DEFAULT_EXPIRE_TIME);
    }
    default boolean lock(String key, Duration expire) {
        return lock(key, expire, DEFAULT_RETRY_TIMES);
    }
    default boolean lock(String key, long expire, TimeUnit unit) {
        return lock(key, Duration.of(expire, unit.toChronoUnit()));
    }
    default boolean lock(String key, Duration expire, int retryTimes){
        return lock(key, expire, retryTimes, DEFAULT_RETRY_INTERVAL);
    }
    default boolean lock(String key, Duration expire, Duration retryInterval){
        return lock(key, expire, DEFAULT_RETRY_TIMES, retryInterval);
    }
    default boolean tryLock(String key, Duration expire){
        return lock(key, expire, 0);
    }
    default boolean tryLock(String key, long expire, TimeUnit unit){
        return tryLock(key, Duration.of(expire, unit.toChronoUnit()));
    }

    /**
     * 解锁
     * @param key   锁标识
     * @return      是否解锁成功.
     */
    boolean unlock(String key);
}
