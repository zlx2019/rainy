package com.zero.rainy.limiter;

import com.zero.rainy.limiter.annotations.ApiLimiter;

import java.time.Duration;

/**
 * 限流器
 *
 * @author Zero.
 * <p> Created on 2025/2/28 14:40 </p>
 */
public interface Limiter {
    /**
     * 尝试获取许可
     * @param key   限流资源标识
     * @return      是否获取到
     */
    boolean tryAcquire(String key, ApiLimiter apiLimiter);

    /**
     * 获取许可
     * @param key       资源标识
     * @param timeout   获取超时时间
     * @return          是否获取到
     */
    boolean acquire(String key, Duration timeout, ApiLimiter apiLimiter);
}
