package com.zero.rainy.core.converts;

import cn.hutool.core.date.DatePattern;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * LocalDateTime 转换器
 * {@link String} -> {@link LocalDateTime}
 *
 * @author Zero.
 * <p> Created on 2024/9/30 17:16 </p>
 */
public class LocalDateTimeConvert implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(String source) {
        return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)
                .withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault()));
    }
}
