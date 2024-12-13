package com.zero.rainy.cache.consts;

/**
 * Redis lua script
 *
 * @author Zero.
 * <p> Created on 2024/11/27 17:33 </p>
 */
public interface Scripts {

    /**
     * 接口限流
     */
    String LIMIT_SCRIPTS = """
            local key = KEYS[1]
            local value = ARGV[1]
            local expire = tonumber(ARGV[2])
            local result = redis.call("SET", key, value, "NX", "PX", expire)
            if result == "OK" then
                return 0
            else
                local newValue = redis.call("INCR", key)
                return newValue
            end
    """;

    /**
     * 释放分布式锁
     */
    String UNLOCK_SCRIPTS = """
        if redis.call('get', KEYS[1]) == ARGV[1] then
            return redis.call('del', KEYS[1])
        else
            return 0
        end
    """;
}
