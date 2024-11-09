package com.zero.rainy.user.config;

import com.zero.rainy.core.config.DefaultConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * 服务默认配置注入
 *
 * @author Zero.
 * <p> Created on 2024/9/2 15:44 </p>
 */
@Configuration
public class SampleDefaultConfig extends DefaultConfigurer {

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setConnectionFactory(factory);
//        return template;
//    }

}
