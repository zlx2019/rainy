package com.zero.rainy.limiter;

import com.zero.rainy.cache.template.CacheTemplate;
import com.zero.rainy.limiter.annotations.ApiLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 分布式限流器(Redis)
 *
 * @author Zero.
 * <p> Created on 2025/2/28 14:45 </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DistributedLimiter implements Limiter {
    private final CacheTemplate cacheTemplate;

    @Override
    public boolean tryAcquire(String key, ApiLimiter limiter) {
        Long rate = cacheTemplate.incrEx(key, Duration.of(limiter.timeWindow(), limiter.timeUnit()));
        return limiter.limits() >= rate;
    }

    @Override
    public boolean acquire(String key, Duration timeout, ApiLimiter limiter) {
        throw new UnsupportedOperationException("[DistributedLimiter] acquirePermission not impl");
    }
}
