package com.zero.rainy.cache.enums;

import lombok.Getter;

import java.time.Duration;

/**
 * Redis 全局缓存Key
 *
 * @author Zero.
 * <p> Created on 2025/2/27 23:09 </p>
 */
@Getter
public enum RedisKeys implements RedisKey{
    TEMPORARY_BLACK_LIST("common", "limit", "temporary-black-list", "临时黑名单"),
    ;
    private final String service;
    private final String module;
    private final String business;
    private final Duration expire;
    private final String desc;

    RedisKeys(String service, String module, String business, String desc) {
        this(service, module, business, Duration.ZERO, desc);
    }

    RedisKeys(String service, String module, String business, Duration expire, String desc) {
        this.service = service;
        this.module = module;
        this.business = business;
        this.expire = expire;
        this.desc = desc;
    }
}
