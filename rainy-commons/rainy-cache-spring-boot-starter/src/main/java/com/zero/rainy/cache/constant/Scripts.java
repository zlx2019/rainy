package com.zero.rainy.cache.constant;

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


    /**
     * 获取大于指定分支的元素中最高分值元素（也就是最新的元素）
     */
    String ZPOP_BY_SCORE_AND_MAX = """
        local key = KEYS[1]
        local threshold_score = tonumber(ARGV[1])
        -- 获取最大分值元素
        local max_result = redis.call('ZREVRANGEBYSCORE', key, '+inf', threshold_score, 'WITHSCORES', 'LIMIT', 0, 1)
        if #max_result > 0 then
            local max_member = max_result[1]
            -- 移除(弹出)该元素
            redis.call('ZREM', key, max_member)
            return max_member
        end
        -- 不存在符合条件的元素
        return nil
    """;

    /**
     * 获取大于指定分支的元素中最低分值元素（区间内最旧的元素）
     */
    String ZPOP_BY_SCORE_AND_MIN = """
        local key = KEYS[1]
        local threshold_score = tonumber(ARGV[1])
        -- 修改为 ZRANGEBYSCORE，并调整分数范围为 [threshold_score, +inf]
        local min_result = redis.call('ZRANGEBYSCORE', key, threshold_score, '+inf', 'WITHSCORES', 'LIMIT', 0, 1)
        if #min_result > 0 then
            local min_member = min_result[1]
            -- 移除(弹出)该元素
            redis.call('ZREM', key, min_member)
            return min_member
        end
        -- 不存在符合条件的元素
        return nil
        """;

    /**
     * 覆盖一个已存在的Key，并且继承它的剩余有效期
     */
    String PUT_IF_EXIST_USE_PREV_TTL = """
            local key = KEYS[1]
            local value = ARGV[1]
            local ttl = redis.call('ttl', KEYS[1])
            if ttl and ttl > 0 then
                redis.call('setex', KEYS[1], ttl, ARGV[1])
                return nil
            end
            return nil
            """;

    /**
     * 自增，如果是首次则设置有效期(ms)
     */
    String INCR_BY_EX = """
            local current = redis.call('incrby', KEYS[1], ARGV[1])
            if current == tonumber(ARGV[1]) then
                redis.call('pexpire', KEYS[1], ARGV[2])
            end
            return current
            """;

}
