package com.zero.rainy.message.utils;

import cn.hutool.core.util.IdUtil;
import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.model.message.supers.BaseMessage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.MDC;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

/**
 * 消息相关工具类
 *
 * @author Zero.
 * <p> Created on 2024/9/28 13:55 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageUtils {
    private static final int DEFAULT_KEY_LENGTH = 10;

    /**
     * 将基础业务消息，包装为 {@link Message<T>}
     * @param message   基础消息
     * @return          Spring 消息
     */
//    public static <T extends BaseMessage> Message<T> buildMessage(T message) {
//        if (StringUtils.isBlank(message.getKeys())){
//            message.setKeys(RandomUtil.randomString(10));
//        }
//        message.setSendTime(LocalDateTime.now());
//        return MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKeys()).build();
//    }
//    public static <T extends BaseMessage> Collection<Message<T>> buildMessages(Collection<T> messages) {
//        return messages.stream().map(MessageUtils::buildMessage).collect(Collectors.toList());
//    }

    /**
     * 将消息载体包装为 {@link Message<T>}
     * @param payload   消息载体
     * @return          Spring 消息
     */
    public static <T> Message<T> buildMessage(T payload) {
        Objects.requireNonNull(payload, "Payload must not be null");
        String key;
        if (payload instanceof BaseMessage message){
            message.setSendTime(LocalDateTime.now());
            key = StringUtils.isNotBlank(message.getKeys()) ? message.getKeys() : generateMessageKey();
            message.setKeys(key);
        }else {
            key = generateMessageKey();
        }
        MessageBuilder<T> builder = MessageBuilder.withPayload(payload).setHeader(RocketMQHeaders.KEYS, key);
        String traceId = MDC.get(Constant.TRACE_ID_LOG_KEY);
        if (StringUtils.isNotBlank(traceId)) {
            builder.setHeader(Constant.TRACE_ID_MESSAGE_KEY, traceId);
        }
        return builder.build();
    }
    public static <T> Collection<Message<T>> buildMessage(Collection<T> payloads) {
        return payloads.stream()
                .filter(Objects::nonNull)
                .map(MessageUtils::buildMessage)
                .toList();
    }

    private static String generateMessageKey(){
        return IdUtil.fastUUID();
    }
}
