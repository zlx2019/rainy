package com.zero.rainy.core.interceptors;

import com.zero.rainy.core.holder.UserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 用户上下文传递拦截器
 *
 * @author Zero.
 * <p> Created on 2024/8/30 18:59 </p>
 */
@Slf4j
public class UserContextInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求头中的用户信息，添加到当前请求上下文
     * @param request   请求
     * @param response  响应
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.clear();
    }
}
