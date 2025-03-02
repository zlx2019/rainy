package com.zero.rainy.limiter;

import com.zero.rainy.limiter.annotations.ApiLimiter;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 单机限流器(resilience4j)
 *
 * @author Zero.
 * <p> Created on 2025/2/28 14:45 </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StandaloneLimiter implements Limiter, RegistryEventConsumer<RateLimiter> {
    /**
     * 默认限流配置 时间窗口60s，许可数量10.
     */
    private final RateLimiterConfig DEFAULT_CONFIG = RateLimiterConfig.custom()
            // 时间窗口周期
            .limitRefreshPeriod(Duration.ofSeconds(60))
            // 周期的许可数量
            .limitForPeriod(10)
            // 等待许可超时时间
            .timeoutDuration(Duration.ZERO)
            // 是否开启堆栈追踪
            .writableStackTraceEnabled(false)
            .build();
    /**
     * 限流管理器
     */
    private final RateLimiterRegistry REGISTRY = RateLimiterRegistry.custom()
            .withRateLimiterConfig(DEFAULT_CONFIG)
            .addRegistryEventConsumer(this)
            .build();

    @Override
    public boolean tryAcquire(String key, ApiLimiter limiter) {
        // 构建限流配置
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.of(limiter.timeWindow(), limiter.timeUnit()))
                .limitForPeriod(limiter.limits())
                .timeoutDuration(Duration.ZERO).build();
        // 获取 or 创建限流器
        RateLimiter rateLimiter = REGISTRY.rateLimiter(key, config);
        boolean permission = rateLimiter.acquirePermission();
        if (permission) {
            rateLimiter.onSuccess();
        }
        return permission;
    }

    @Override
    public boolean acquire(String key, Duration timeout, ApiLimiter apiLimiter) {
        return false;
    }

    @Override
    public void onEntryAddedEvent(EntryAddedEvent<RateLimiter> event) {
        RateLimiter limiter = event.getAddedEntry();
        log.info("[StandaloneLimiter] Register limiter: [{}]", limiter.getName());
        limiter.getEventPublisher()
                .onEvent(e-> {
                    log.info("[StandaloneLimiter] [{}] event [{}]", limiter.getName(), e.getEventType());
                });
    }

    @Override
    public void onEntryRemovedEvent(EntryRemovedEvent<RateLimiter> event) {
        log.info("[StandaloneLimiter] Remove limiter: [{}]", event.getRemovedEntry().getName());
    }

    @Override
    public void onEntryReplacedEvent(EntryReplacedEvent<RateLimiter> event) {
        log.info("[StandaloneLimiter] Replaced limiter: [{}] --> [{}]", event.getNewEntry().getName(), event.getOldEntry().getName());
    }
}
