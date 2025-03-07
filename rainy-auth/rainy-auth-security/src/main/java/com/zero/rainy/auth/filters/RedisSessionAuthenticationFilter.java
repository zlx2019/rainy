package com.zero.rainy.auth.filters;

import com.zero.rainy.auth.enums.AuthRedisKeys;
import com.zero.rainy.cache.enums.RedisKey;
import com.zero.rainy.cache.utils.RedisHelper;
import com.zero.rainy.security.filters.AuthenticationAbstractFilter;
import com.zero.rainy.security.properties.SecurityProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

/**
 * 授权凭证 Redis会话校验过滤器
 *
 * @author Zero.
 * <p> Created on 2025/3/7 18:37 </p>
 */
@Slf4j
public class RedisSessionAuthenticationFilter extends AuthenticationAbstractFilter {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisKey TOKEN_KEY = AuthRedisKeys.USER_AUTH_TOKEN;
    public RedisSessionAuthenticationFilter(RedisTemplate<String, Object> redisTemplate, SecurityProperties securityProperties) {
        super(securityProperties);
        this.redisTemplate = redisTemplate;
    }

    /**
     * Validate authorize session
     */
    @Override
    protected boolean doValidateSession(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String username, String token) throws ServletException, IOException {
        String accessTokenKey = RedisHelper.keyBuild(TOKEN_KEY, username, "access-token");
        Object tokenValue = redisTemplate.opsForValue().get(accessTokenKey);
        if (tokenValue instanceof String value){
            return value.equals(token);
        }
        return false;
    }
}
