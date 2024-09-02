package com.zero.rainy.core.config;

import com.zero.rainy.core.interceptors.LogTraceInterceptor;
import com.zero.rainy.core.interceptors.UserContextInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局默认Spring Mvc 配置
 *
 * @author Zero.
 * <p> Created on 2024/9/1 22:25 </p>
 */
public class DefaultWebConfigurer implements WebMvcConfigurer {


    /**
     * 拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册链路追踪拦截器
        registry.addInterceptor(new LogTraceInterceptor()).addPathPatterns("/**");
        // 注册用户信息拦截器
        registry.addInterceptor(new UserContextInterceptor()).addPathPatterns("/**");
    }

}
