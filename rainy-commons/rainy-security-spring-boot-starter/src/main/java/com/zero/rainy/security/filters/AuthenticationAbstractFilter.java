package com.zero.rainy.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.zero.rainy.core.enums.GlobalResponseCode;
import com.zero.rainy.security.helper.TokenHelper;
import com.zero.rainy.security.properties.SecurityProperties;
import com.zero.rainy.web.utils.ResponseUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 授权验证前置过滤器
 *
 * @author Zero.
 * <p> Created on 2025/3/6 18:53 </p>
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("all")
public abstract class AuthenticationAbstractFilter extends OncePerRequestFilter {
    private final AntPathMatcher matcher = new AntPathMatcher();
    private final SecurityProperties securityProperties;

    /**
     * Filter logic
     * 过滤器逻辑
     * @param request       http request
     * @param response      http response
     * @param filterChain   request filter chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        this.doValidateAuthorize(request, response, filterChain);
    }

    /**
     * Validate logic
     * 授权令牌校验
     */
    protected void doValidateAuthorize(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(token)) {
            log.error("[Auth] token is blank, Request: {}", request.getRequestURI());
            ResponseUtils.response(response, GlobalResponseCode.UNAUTHORIZED);
            filterChain.doFilter(request, response);
            return;
        }
        // step1: 校验令牌，获取其用户信息
        Authentication authentication;
        try {
            // TODO 抽取为JWT，并解析为 Authentication and UserDetails
            authentication = TokenHelper.extractToken(token);
        }catch (TokenExpiredException e) {
            log.error("[Auth] token expired, Request: {}", request.getRequestURI());
            ResponseUtils.response(response, GlobalResponseCode.AUTHORIZED_EXPIRED);
            return;
        }catch (JWTVerificationException e){
            log.error("[Auth] token is invalid, Request: {} by: {}", request.getRequestURI(), e.getMessage());
            ResponseUtils.response(response, GlobalResponseCode.AUTHORIZED_INVALID);
            return;
        }
        // step2: 校验会话是否有效
        this.doValidateSession(request, response, filterChain);
        if (!this.doValidateSession(request, response, filterChain)) {
            // 令牌已离线(修改密码、强制下线等)
            log.error("[Auth] token offline, Request: {}", request.getRequestURI());
            ResponseUtils.response(response, GlobalResponseCode.AUTHORIZED_INVALID);
            return;
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    /**
     * can it be skipped current filter
     * @param request current HTTP request
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return securityProperties.getIgnoreUrls().stream().anyMatch(ignoreUrl -> matcher.match(ignoreUrl, request.getServletPath()));
    }

    /**
     * Validate authorize session
     * 校验授权会话，是否有效
     */
    protected abstract boolean doValidateSession(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException;
}
