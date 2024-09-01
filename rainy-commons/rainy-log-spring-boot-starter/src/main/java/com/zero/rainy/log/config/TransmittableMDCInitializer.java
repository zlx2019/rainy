package com.zero.rainy.log.config;

import org.slf4j.TransmittableMDCAdapter;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * TransmittableMDCAdapter 初始化加载
 *
 * @author Zero.
 * <p> Created on 2024/9/1 21:50 </p>
 */
public class TransmittableMDCInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    /**
     * 初始化回调，将TransmittableMDCAdapter单例实例加载，并设置为 MDC的默认实现
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TransmittableMDCAdapter.getInstance();
    }
}
