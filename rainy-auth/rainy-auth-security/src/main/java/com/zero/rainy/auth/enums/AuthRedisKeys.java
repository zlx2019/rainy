package com.zero.rainy.auth.enums;

import com.zero.rainy.cache.enums.RedisKey;
import lombok.Getter;

import java.time.Duration;

/**
 * @author Zero.
 * <p> Created on 2025/3/6 13:29 </p>
 */
@Getter
public enum AuthRedisKeys implements RedisKey {
    USER_AUTH_TOKEN("uaa", "auth", "token", "用户令牌会话")

    ;
    private final String service;
    private final String module;
    private final String business;
    private final Duration expire;
    private final String desc;

    AuthRedisKeys(String service, String module, String business, String desc) {
        this(service, module, business, Duration.ZERO, desc);
    }

    AuthRedisKeys(String service, String module, String business, Duration expire, String desc) {
        this.service = service;
        this.module = module;
        this.business = business;
        this.expire = expire;
        this.desc = desc;
    }
}
