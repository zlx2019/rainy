package com.zero.rainy.core.converts;

import cn.hutool.core.date.DatePattern;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * LocalDate 反序列转换器
 * {@link String} -> {@link LocalDateTime}
 *
 * @author Zero.
 * <p> Created on 2024/9/30 17:16 </p>
 */
public class LocalDateConvert implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source, DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN).withLocale(Locale.getDefault()).withZone(ZoneId.systemDefault()));
    }
}
