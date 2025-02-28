package com.zero.rainy.cache.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.TypeResolverBuilder;
import com.zero.rainy.cache.ext.lock.RedisDistributedLock;
import com.zero.rainy.cache.subscriber.DynamicConfigSubscriber;
import com.zero.rainy.cache.template.provider.RedisProvide;
import com.zero.rainy.core.ext.dynamic.DynamicConfigConstant;
import com.zero.rainy.core.lock.DistributedLock;
import com.zero.rainy.core.utils.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
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
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, RedisSerializer<Object> valueSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        StringRedisSerializer keySerializer = getKeySerializer();
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
    @Bean
    @Primary
    public RedisSerializer<Object> getValueSerializer() {
        ObjectMapper mapper = JsonUtils.getMapper();
        // 设置可见性
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 自定义类型处理器,保留类型信息，方便序列化
        TypeResolverBuilder<?> typeResolver = new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL) {
            @Override
            public boolean useForType(JavaType t) {
                // 排除集合类型
                if (t.isCollectionLikeType() || t.isArrayType()) {
                    return false;
                }
                return super.useForType(t);
            }
        };
        typeResolver.init(JsonTypeInfo.Id.CLASS, null);
        typeResolver.inclusion(JsonTypeInfo.As.PROPERTY);
        typeResolver.typeProperty("@class");
        mapper.setDefaultTyping(typeResolver);
//        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        // 忽略 null 值
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return new Jackson2JsonRedisSerializer<>(mapper, Object.class);
    }

    /**
     * 注册 Redis 订阅者监听器
     */
    @Bean
    public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory connectionFactory, RedisSerializer<Object> valueSerializer) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(new DynamicConfigSubscriber(valueSerializer), new ChannelTopic(DynamicConfigConstant.EVENT_BUS_TOPIC));
        return container;
    }

    /**
     * 注入 Redis 分布式锁
     */
    @Bean
    public DistributedLock distributedLock(RedisTemplate<String, Object> redisTemplate) {
        return new RedisDistributedLock(redisTemplate);
    }
}
