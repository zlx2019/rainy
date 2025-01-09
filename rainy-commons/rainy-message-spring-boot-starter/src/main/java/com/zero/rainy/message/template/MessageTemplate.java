package com.zero.rainy.message.template;


import com.zero.rainy.message.model.delay.DelayMessage;
import com.zero.rainy.message.utils.MessageUtils;

import java.time.Duration;
import java.util.Collection;

/**
 * 消息服务模板接口
 *
 * @author Zero.
 * <p> Created on 2024/9/27 16:03 </p>
 */
public interface MessageTemplate {

    String METHOD_NOT_IMPLEMENTS = "Method not implements";

    /**
     * 发送同步消息（消息类型任意）
     *
     * @param topic     目标主题
     * @param payload   消息载体
     * @param tags      消息标签
     * @return          发送结果
     */
    default <T> boolean send(String topic, T payload, String... tags){
        throw new UnsupportedOperationException(METHOD_NOT_IMPLEMENTS);
    }

    /**
     * 批量发送同步消息（消息类型任意）
     * @param topic     目标主题
     * @param payloads  消息载体列表
     * @param tags      消息标签
     * @return          发送结果
     */
    default <T> boolean sendBatch(String topic, Collection<T> payloads, String... tags){
        throw new UnsupportedOperationException(METHOD_NOT_IMPLEMENTS);
    }

    /**
     * 发送同步延迟消息
     * @param topic         目标主题
     * @param payload       消息载体
     * @param tag           标签
     */
    <T> boolean sendDelay(String topic, T payload, Duration delay, String... tag);

    /**
     * 批量发送同步延迟消息
     * @param topic     目标主题
     * @param payloads  消息载体列表
     * @param delay     延迟时间
     * @param tag       消息标签
     */
    default <T> boolean sendDelay(String topic, Collection<T> payloads, Duration delay, String... tag){
        return MessageUtils.buildMessage(payloads).stream().allMatch(message-> this.sendDelay(topic, message, delay, tag));
    }

    default <T extends DelayMessage> boolean sendDelay(String topic, T message, String... tag){
        return sendDelay(topic, message, message.getDelay(), tag);
    }

    default <T extends DelayMessage> boolean sendDelay(String topic, Collection<T> messages, String... tag){
        return messages.stream().allMatch(message-> this.sendDelay(topic, message, tag));
    }
}
