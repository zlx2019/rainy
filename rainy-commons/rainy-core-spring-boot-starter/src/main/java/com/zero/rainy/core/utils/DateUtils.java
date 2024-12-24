package com.zero.rainy.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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

    public static Date min(Date d1, Date d2){
        Date now = new Date();
        long diff1 = Math.abs(now.getTime() - d1.getTime());
        long diff2 = Math.abs(now.getTime() - d2.getTime());
        return diff1 < diff2 ? d1 : d2;
    }

    public static LocalDateTime min(LocalDateTime d1, LocalDateTime d2){
        LocalDateTime now = LocalDateTime.now();
        long diff1 = Math.abs(ChronoUnit.MILLIS.between(now, d1));
        long diff2 = Math.abs(ChronoUnit.MILLIS.between(now, d2));
        return diff1 < diff2 ? d1 : d2;
    }

    public static void main(String[] args) {
        LocalDateTime time1 = LocalDateTime.now().minusSeconds(10);
        LocalDateTime time2 = LocalDateTime.now().minusSeconds(6);
        LocalDateTime min = min(time1, time2);
        System.out.println(time1 == min);
    }
}
