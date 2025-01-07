package com.zero.rainy.log;

import com.zero.rainy.log.properties.LoggerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 日志组件自动装配
 *
 * @author Zero.
 * <p> Created on 2024/9/1 22:03 </p>
 */
@EnableConfigurationProperties(LoggerProperties.class)
public class LoggerAutoConfiguration {

}
