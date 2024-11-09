package com.zero.rainy.core.annotations;

import com.zero.rainy.core.enums.DynamicConfigKeys;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启动态配置功能注解
 *
 * @author Zero.
 * <p> Created on 2024/11/6 22:06 </p>
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableDynamicConfiguration {
    DynamicConfigKeys value();
}
