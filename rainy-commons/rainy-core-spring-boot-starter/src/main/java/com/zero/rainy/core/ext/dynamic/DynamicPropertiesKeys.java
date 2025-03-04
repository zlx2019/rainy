package com.zero.rainy.core.ext.dynamic;

import com.zero.rainy.core.enums.DynamicPropertiesKey;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态配置标记
 *
 * @author Zero.
 * <p> Created on 2024/11/6 22:06 </p>
 */
@Component
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DynamicPropertiesKeys {
    DynamicPropertiesKey value();
}
