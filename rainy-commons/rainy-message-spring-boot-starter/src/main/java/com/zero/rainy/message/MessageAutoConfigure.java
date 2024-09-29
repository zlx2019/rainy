package com.zero.rainy.message;

import com.zero.rainy.message.config.RocketMQConfigure;
import org.springframework.context.annotation.Import;

/**
 * 消息组件自动装配
 *
 * @author Zero.
 * <p> Created on 2024/9/27 16:02 </p>
 */
@Import(RocketMQConfigure.class)
public class MessageAutoConfigure {

}
