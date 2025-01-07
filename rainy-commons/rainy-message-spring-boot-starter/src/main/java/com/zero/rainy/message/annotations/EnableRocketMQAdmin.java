package com.zero.rainy.message.annotations;

import com.zero.rainy.message.config.RocketMQAdminConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启 RocketMQ Admin 功能
 *
 * @author Zero.
 * <p> Created on 2025/1/7 16:51 </p>
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RocketMQAdminConfig.class})
public @interface EnableRocketMQAdmin {

}
