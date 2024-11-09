package com.zero.rainy.core.ext.dynamic;

import com.zero.rainy.core.annotations.EnableDynamicConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Zero.
 * <p> Created on 2024/11/6 22:50 </p>
 */
@Component
@RequiredArgsConstructor
public class DynamicConfigProcessor implements BeanPostProcessor {
    private final DynamicConfigManager dynamicConfigManager;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        EnableDynamicConfiguration annotation = beanClass.getAnnotation(EnableDynamicConfiguration.class);
        if (annotation != null) {
            System.out.println("DynamicConfigProcessor - postProcessBeforeInitialization");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        EnableDynamicConfiguration annotation = beanClass.getAnnotation(EnableDynamicConfiguration.class);
        if (annotation != null) {
            System.out.println("DynamicConfigProcessor - postProcessAfterInitialization");
        }
        return bean;
    }
}
