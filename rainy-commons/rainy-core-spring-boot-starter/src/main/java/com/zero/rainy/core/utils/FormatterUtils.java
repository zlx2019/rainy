package com.zero.rainy.core.utils;

import cn.hutool.core.date.DatePattern;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 格式化工具类
 *
 * @author Zero.
 * <p> Created on 2024/9/17 17:54 </p>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormatterUtils {
    private static final ConcurrentDateFormat FORMAT = new ConcurrentDateFormat(DatePattern.NORM_DATETIME_PATTERN, Locale.getDefault(), TimeZone.getDefault());
    public static final DateTimeFormatter LOCAL_FORMAT = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN, Locale.getDefault());


    /**
     * Date format to string
     */
    public static String format(Date date){
        return FORMAT.format(date);
    }
    /**
     * LocalDate format to string
     */
    public static String format(LocalDate date){
        return LOCAL_FORMAT.format(date);
    }
    /**
     * LocalDateTime format to string
     */
    public static String format(LocalDateTime dateTime){
        return LOCAL_FORMAT.format(dateTime);
    }

    /**
     * string format Date
     * @throws ParseException 格式化异常
     */
    public static Date parseDate(String str) throws ParseException {
        return FORMAT.parse(str);
    }

    /**
     * string format LocalDateTime
     */
    public static LocalDateTime parseDateTime (String str){
        return LocalDateTime.parse(str, LOCAL_FORMAT);
    }

    /**
     * 线程安全且高效的 {@link java.util.Date} 格式化器
     */
    private static class ConcurrentDateFormat{
        private final String pattern;
        private final Locale locale;
        private final TimeZone timeZone;
        private final Queue<SimpleDateFormat> formats = new ConcurrentLinkedQueue<>();

        private ConcurrentDateFormat(String pattern, Locale locale, TimeZone timeZone){
            this.pattern = pattern;
            this.locale = locale;
            this.timeZone = timeZone;
            formats.add(this.newInstance());
        }
        private SimpleDateFormat newInstance(){
            SimpleDateFormat format = new SimpleDateFormat(this.pattern, this.locale);
            format.setTimeZone(this.timeZone);
            return format;
        }
        public Date parse(String str) throws ParseException {
            SimpleDateFormat format = formats.poll();
            if(format == null){
                format = this.newInstance();
            }
            Date date = format.parse(str);
            formats.add(format);
            return date;
        }
        public String format(Date date){
            SimpleDateFormat format = formats.poll();
            if(format == null){
                format = this.newInstance();
            }
            String dateStr = format.format(date);
            formats.add(format);
            return dateStr;
        }
    }
}
