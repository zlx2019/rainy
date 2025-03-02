package com.zero.rainy.core.ext.dynamic;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Zero.
 * <p> Created on 2024/12/27 11:36 </p>
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({DynamicConfigManager.class, DynamicConfigProcessor.class})
public @interface EnableDynamicProperties {

}
