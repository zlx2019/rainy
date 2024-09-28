package com.zero.rainy.message.utils;

import cn.hutool.core.util.RandomUtil;
import com.zero.rainy.core.pojo.message.BaseMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;

/**
 * 消息相关工具类
 *
 * @author Zero.
 * <p> Created on 2024/9/28 13:55 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtils {


    public static <T extends BaseMessage> Message<T> buildMessage(T message) {
        if (StringUtils.isBlank(message.getKeys())){
            message.setKeys(RandomUtil.randomString(10));
        }
        message.setSendTime(LocalDateTime.now());
        return MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKeys()).build();
    }
}
