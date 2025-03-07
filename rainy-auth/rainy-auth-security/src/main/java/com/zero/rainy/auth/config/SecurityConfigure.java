package com.zero.rainy.auth.config;

import com.zero.rainy.auth.filters.RedisSessionAuthenticationFilter;
import com.zero.rainy.auth.service.impl.RedisOneTimeTokenManager;
import com.zero.rainy.security.filters.AuthenticationAbstractFilter;
import com.zero.rainy.security.properties.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.ott.OneTimeTokenService;

/**
 * @author Zero.
 * <p> Created on 2025/3/7 16:47 </p>
 */
@Configuration
public class SecurityConfigure {

    /**
     * OTT token service
     */
    @Bean
    public OneTimeTokenService oneTimeTokenService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisOneTimeTokenManager(redisTemplate);
    }


    @Bean
    @Primary
    public AuthenticationAbstractFilter authenticationAbstractFilter(RedisTemplate<String, Object> redisTemplate, SecurityProperties securityProperties){
        return new RedisSessionAuthenticationFilter(redisTemplate, securityProperties);
    }
}
