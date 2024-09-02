package com.zero.rainy.core.config;

import org.springframework.context.annotation.Import;

/**
 * 全局默认服务配置注入
 *
 * @author Zero.
 * <p> Created on 2024/9/2 15:43 </p>
 */
@Import({
        DefaultWebConfigurer.class,
        DefaultJacksonConfigurer.class,
        DefaultThreadPoolConfigurer.class
})
public class DefaultConfigurer { }
