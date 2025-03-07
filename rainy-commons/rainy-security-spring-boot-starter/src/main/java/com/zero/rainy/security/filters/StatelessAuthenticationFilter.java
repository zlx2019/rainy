package com.zero.rainy.security.filters;

import com.zero.rainy.security.properties.SecurityProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 默认的无会话授权校验过滤器 (令牌不存在状态，无法将其失效)
 *
 * @author Zero.
 * <p> Created on 2025/3/4 14:19 </p>
 */
@Slf4j
@Component
public class StatelessAuthenticationFilter extends AuthenticationAbstractFilter {
    public StatelessAuthenticationFilter(SecurityProperties securityProperties) {
        super(securityProperties);
    }

    /**
     * 授权会话是否有效, 以JWT有效期为准，所以默认为有效.
     */
    @Override
    protected boolean doValidateSession(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String username, String token) throws ServletException, IOException {
        return Boolean.TRUE;
    }
}
