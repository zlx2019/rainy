package com.zero.rainy.auth.config;

import com.zero.rainy.core.model.Result;
import com.zero.rainy.auth.enums.AuthResponseCode;
import com.zero.rainy.web.handler.DefaultExceptionAdvice;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常捕获处理
 *
 * @author Zero.
 * <p> Created on 2024/9/16 12:28 </p>
 */
@Slf4j
@ControllerAdvice
public class ExceptionConfigure extends DefaultExceptionAdvice {

    /**
     * 登录异常处理
     * @param e 异常
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public Result<Void> handleAuthenticationException(AuthenticationException e, HttpServletRequest request, HttpServletResponse response) {
        log.error("[Login] Login fail: {}", e.getMessage());
        return super.exceptionHandler(response, AuthResponseCode.LOGIN_ERROR);
    }
}
