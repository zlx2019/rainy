package com.zero.rainy.security.handler;

import com.zero.rainy.core.enums.GlobalResponseCode;
import com.zero.rainy.web.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 令牌认证失败异常处理
 *
 * @author Zero.
 * <p> Created on 2025/3/4 15:02 </p>
 */
@Slf4j
@Component
public class DefaultAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.error("[Auth] auth fail: {}", e.getMessage());
        ResponseUtils.response(response, GlobalResponseCode.UNAUTHORIZED);
    }
}
