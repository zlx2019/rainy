package com.zero.rainy.web.interceptors;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.holder.UserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

/**
 * 用户上下文传递拦截器
 *
 * @author Zero.
 * <p> Created on 2024/8/30 18:59 </p>
 */
@Slf4j
@SuppressWarnings("all")
public class UserContextInterceptor implements HandlerInterceptor {

    /**
     * 拦截请求头中的用户信息，添加到当前用户上下文 {@link UserContextHolder}
     *
     * @param request   请求
     * @param response  响应
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional.ofNullable(request.getHeader(Constant.USER_ID_HEADER_KEY)).filter(StrUtil::isNotBlank).ifPresent(UserContextHolder::setUser);
        UserContextHolder.setUser(String.valueOf(IdUtil.getSnowflakeNextId()));
        return true;
    }

    /**
     * 请求结束，清除用户上下文信息
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContextHolder.clear();
    }
}
