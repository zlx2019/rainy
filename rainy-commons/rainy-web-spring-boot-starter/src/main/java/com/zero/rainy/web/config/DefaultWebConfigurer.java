package com.zero.rainy.web.config;

import com.zero.rainy.core.ext.converts.LocalDateConvert;
import com.zero.rainy.core.ext.converts.LocalDateTimeConvert;
import com.zero.rainy.core.ext.converts.OrderByConvert;
import com.zero.rainy.web.interceptors.LogTraceInterceptor;
import com.zero.rainy.web.interceptors.UserContextInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
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
     * 注册自定义拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册链路追踪拦截器
        registry.addInterceptor(new LogTraceInterceptor()).addPathPatterns("/**");
        // 注册用户信息拦截器
        registry.addInterceptor(new UserContextInterceptor()).addPathPatterns("/**");
    }

    /**
     * 注册自定义转换器
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new OrderByConvert());
        registry.addConverter(new LocalDateTimeConvert());
        registry.addConverter(new LocalDateConvert());
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }

}
