package com.zero.rainy.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.zero.rainy.core.utils.JsonUtils;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;

/**
 * Redis 配置
 *
 * @author Zero.
 * <p> Created on 2024/9/29 16:16 </p>
 */
public class RedisConfigure {


    /**
     * Redis Key 序列化器
     */
    private StringRedisSerializer getKeySerializer(){
        return new StringRedisSerializer(StandardCharsets.UTF_8);
    }

    /**
     * Redis Value 序列化器
     */
    private Jackson2JsonRedisSerializer<Object> getValueSerializer(){
        ObjectMapper objectMapper = JsonUtils.getMapper().copy();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        return new Jackson2JsonRedisSerializer<>(objectMapper, Object.class);
    }
}
