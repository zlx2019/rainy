package com.zero.rainy.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.zero.rainy.cache.template.provider.RedisProvide;
import com.zero.rainy.core.utils.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Default Redis Config
 *
 * @author Zero.
 * <p> Created on 2024/10/2 10:32 </p>
 */
public class RedisConfig {
    /**
     * 注入 Redis 客户端模板
     * @param factory   连接工厂
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        StringRedisSerializer keySerializer = getKeySerializer();
        RedisSerializer<Object> valueSerializer = getValueSerializer();
        template.setKeySerializer(keySerializer);
        template.setValueSerializer(valueSerializer);
        template.setHashKeySerializer(keySerializer);
        template.setHashValueSerializer(valueSerializer);
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 注入自定义缓存客户端
     * @param template  Redis 客户端
     */
    @Bean
    public RedisProvide cacheTemplate(RedisTemplate<String, Object> template) {
        return new RedisProvide(template);
    }

    /**
     * Redis Key 序列化器
     */
    public StringRedisSerializer getKeySerializer() {
        return new StringRedisSerializer();
    }
    /**
     * Redis Value 序列化器
     */
    public RedisSerializer<Object> getValueSerializer() {
        ObjectMapper mapper = JsonUtils.getMapper().copy();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        return new Jackson2JsonRedisSerializer<>(mapper, Object.class);
    }

}