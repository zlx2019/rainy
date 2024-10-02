package com.zero.rainy.core.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Spring Mvc 默认 Jackson 配置
 *
 * @author Zero.
 * <p> Created on 2024/7/29 10:50 </p>
 */
public class DefaultJacksonConfigurer {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.locale(Locale.getDefault())
                    // 序列化时，对象为 null，是否抛异常
                    .failOnEmptyBeans(false)
                    // 反序列化时，json 中包含 pojo 不存在属性时，是否抛异常
                    .failOnUnknownProperties(false)
                    // 禁止将 Date、Calendar 序列化为时间戳
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    // 设置 Date、Calendar 序列化的格式
                    .simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN)
                    // 设置 Date、Calender 序列化时区
                    .timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));

            // 反序列化时采用 小写+下划线形式.
            builder.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            // Java8 时期时间类型序列化
            JavaTimeModule timeModule = new JavaTimeModule();
            timeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            timeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            timeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
            timeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN)));
            timeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN)));
            timeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN)));
            builder.modules(timeModule);
            // BigNumber to String 防止前端精度丢失
            builder.serializerByType(Long.class, ToStringSerializer.instance);
            // long 暂时不转换.
//            builder.serializerByType(Long.TYPE,ToStringSerializer.instance);
            builder.serializerByType(BigInteger.class, ToStringSerializer.instance);
        };
    }

}
