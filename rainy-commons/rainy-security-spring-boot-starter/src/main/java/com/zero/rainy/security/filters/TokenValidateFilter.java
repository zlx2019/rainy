package com.zero.rainy.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zero.rainy.core.enums.GlobalResponseCode;
import com.zero.rainy.security.helper.TokenHelper;
import com.zero.rainy.security.properties.AuthProperties;
import com.zero.rainy.web.utils.ResponseUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * login access-token validate filter
 *
 * @author Zero.
 * <p> Created on 2025/3/4 14:21 </p>
 */
@Slf4j
@Component
@SuppressWarnings("all")
public class TokenValidateFilter extends OncePerRequestFilter {
    private final AntPathMatcher matcher = new AntPathMatcher();
    private final AuthProperties securityProperties;
    public TokenValidateFilter(AuthProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (this.isSkip(request)) {
            filterChain.doFilter(request, response);
        }else {
            this.doValidateToken(request, response, filterChain);
        }
    }

    /**
     * validate token
     */
    protected void doValidateToken(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            log.error("[Auth] token is blank, Request: {}", request.getRequestURI());
            ResponseUtils.response(response, GlobalResponseCode.UNAUTHORIZED);
            return;
        }
        // 解析token，获取认证信息
        Authentication authentication;
        try {
            authentication = TokenHelper.extractToken(token);
        }catch (JWTVerificationException e){
            log.error("[Auth] token is invalid, Request: {} by: {}", request.getRequestURI(), e.getMessage());
            ResponseUtils.response(response, GlobalResponseCode.UNAUTHORIZED);
            return;
        }
        // TODO 用户权限信息
        List<GrantedAuthority> authorities = new ArrayList<>();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
        return;
    }

    /**
     * can it be skipped validate
     * @param request http request
     */
    protected boolean isSkip(HttpServletRequest request) {
        String path = request.getRequestURI().replace(request.getContextPath(), "");
        return securityProperties.getIgnoreUrls().stream().anyMatch(ignoreUrl -> matcher.match(ignoreUrl, path));
    }
}
