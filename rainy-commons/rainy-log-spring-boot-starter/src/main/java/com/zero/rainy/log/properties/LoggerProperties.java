package com.zero.rainy.log.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Zero.
 * <p> Created on 2024/9/1 22:01 </p>
 */
@ConfigurationProperties(prefix = "rainy.log.trace")
public class LoggerProperties {

    /**
     * 是否开启日志模块的链路追踪
     */
    private boolean enabled = true;
}
