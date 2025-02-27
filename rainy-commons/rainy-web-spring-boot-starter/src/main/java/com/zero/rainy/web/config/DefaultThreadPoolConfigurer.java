package com.zero.rainy.web.config;

import com.zero.rainy.core.async.AsyncThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 默认线程池配置
 *
 * @author Zero.
 * <p> Created on 2024/9/2 11:01 </p>
 */
@EnableAsync
public class DefaultThreadPoolConfigurer implements AsyncConfigurer {

    /**
     * 注入 Spring 异步线程池
     */
    @Bean
    @Primary
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        // 使用自定义异步线程池
        AsyncThreadPoolTaskExecutor executor = new AsyncThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);
        executor.setMaxPoolSize(96);
        executor.setQueueCapacity(128);
        executor.setThreadNamePrefix("rainy-async-task-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}
