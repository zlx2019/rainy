package com.zero.rainy.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 时间日期工具类
 *
 * @author Zero.
 * <p> Created on 2024/9/17 21:00 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    /**
     * 当前系统时间，默认时区
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime now(){
        return LocalDateTime.now();
    }

    /**
     * 时间戳 转 {@link LocalDateTime}
     * @param timestamp 时间戳(毫秒)
     */
    public static LocalDateTime of(long timestamp){
        return of(Instant.ofEpochMilli(timestamp));
    }

    /**
     * {@link Instant} 转 {@link LocalDateTime}
     */
    public static LocalDateTime of(Instant instant){
        return of(instant, ZoneId.systemDefault());
    }
    public static LocalDateTime of(Instant instant, ZoneId zoneId){
        return LocalDateTime.ofInstant(instant, zoneId);
    }

}
