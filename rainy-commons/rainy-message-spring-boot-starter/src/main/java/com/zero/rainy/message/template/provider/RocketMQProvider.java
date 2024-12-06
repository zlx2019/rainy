package com.zero.rainy.message.template.provider;

import com.zero.rainy.core.pojo.message.supers.BaseMessage;
import com.zero.rainy.message.template.MessageTemplate;
import com.zero.rainy.message.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * RocketMQ 消息服务提供者
 *
 * @author Zero.
 * <p> Created on 2024/9/27 16:04 </p>
 */
@Slf4j
public class RocketMQProvider implements MessageTemplate {
    private final RocketMQTemplate template;
    private final DefaultMQProducer producer;
    public RocketMQProvider(RocketMQTemplate template) {
        this.template = template;
        this.producer = template.getProducer();
    }

    /**
     * 发送同步消息
     * @param topic     目标主题
     * @param payload   消息载体
     * @param tags      消息标签
     */
    @Override
    public <T> boolean send(String topic, T payload, String... tags) {
        return this.syncSend(buildTopic(topic, tags), MessageUtils.buildMessage(payload));
    }

    /**
     * 批量发送同步消息
     * @param topic     目标主题
     * @param payloads  消息载体列表
     * @param tags      消息标签
     */
    @Override
    public <T> boolean sendBatch(String topic, Collection<T> payloads, String... tags) {
        return this.syncSendBatch(buildTopic(topic, tags), MessageUtils.buildMessage(payloads));
    }


    /**
     * 发送延迟消息
     *
     * @param topic   目标主题
     * @param payload 消息载体
     * @param tag     标签
     */
    @Override
    public <T> boolean sendDelay(String topic, T payload, Duration delay, String... tag) {
        return this.syncSendDelay(buildTopic(topic, tag), MessageUtils.buildMessage(payload), delay);
    }

    /**
     * 发送同步消息
     *
     * @param topic     主题
     * @param message   消息
     * @return          是否发送成功
     */
    private boolean syncSend(String topic, Message<?> message) {
        SendResult sendResult = template.syncSend(topic, message);
        return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
    }

    /**
     * 发送同步延迟消息
     * @param topic     主题
     * @param message   消息
     * @param delay     延迟时间
     * @return          是否发送成功
     */
    private boolean syncSendDelay(String topic, Message<?> message, Duration delay) {
        SendResult sendResult = template.syncSendDelayTimeMills(topic, message, delay.toMillis());
        return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
    }

    /**
     * 批量发送同步消息
     * @return      所有消息是否发送成功
     */
    private <T> boolean syncSendBatch(String topic, Collection<Message<T>> messages) {
        SendResult sendResult = template.syncSend(topic, messages);
        return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
    }


    /**
     * 发送异步消息
     *
     * @param topic     主题
     * @param message   消息
     * @param callback  发送回调
     */
    private void asyncSend(String topic, Message<?> message, SendCallback callback) {
        template.asyncSend(topic, message, callback);
    }

    /**
     * 构建带有 tags 的 topic 名称
     *
     * @param topic     主题
     * @param tags      标签
     * @return          topic:tag
     */
    private String buildTopic(String topic, String... tags) {
        if (ArrayUtils.isNotEmpty(tags) && StringUtils.isNotBlank(tags[0])){
            return topic + ":" + tags[0];
        }
        return topic;
    }
}
