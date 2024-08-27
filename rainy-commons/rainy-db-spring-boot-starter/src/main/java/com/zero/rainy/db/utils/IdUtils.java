package com.zero.rainy.db.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 分布式唯一ID 工具类，基于Snowflake算法优化实现64位自增ID算法。
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:12 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdUtils {
    private static final Snowflake SNOWFLAKE = new Snowflake();
    public static long getNextId(){
        return SNOWFLAKE.nextId();
    }
    public static String getNextIdStr(){
        return SNOWFLAKE.nextIdStr();
    }
    public static void main(String[] args) {
        IdUtil.getSnowflake();
    }
}
