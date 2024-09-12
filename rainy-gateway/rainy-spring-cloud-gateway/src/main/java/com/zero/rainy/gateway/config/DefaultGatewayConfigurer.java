package com.zero.rainy.gateway.config;

import com.zero.rainy.gateway.filter.RequestContextTraceFilter;
import com.zero.rainy.gateway.handler.GatewayExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 网关默认配置
 *
 * @author Zero.
 * <p> Created on 2024/9/12 11:16 </p>
 */
@Slf4j
@Configuration
public class DefaultGatewayConfigurer {

    @Bean
    public GatewayExceptionHandler gatewayExceptionHandler(){
        return new GatewayExceptionHandler();
    }

    /**
     * 日志链路追踪ID生成 过滤器
     */
    @Bean
    public GlobalFilter globalFilter(){
        return new RequestContextTraceFilter();
    }
}
