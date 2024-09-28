package com.zero.rainy.message.template.provider;

import com.zero.rainy.core.pojo.message.BaseMessage;
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
     * 发送同步消息 {@link BaseMessage}
     *
     * @param topic   目标主题
     * @param message 消息
     * @param tag     消息标签
     */
    @Override
    public <T extends BaseMessage> boolean send(String topic, T message, String... tag) {
        if (StringUtils.isBlank(message.getKeys())){
            message.setKeys(UUID.randomUUID().toString());
        }
        message.setSendTime(LocalDateTime.now());
        Message<T> msg = MessageBuilder.withPayload(message)
                .setHeader(RocketMQHeaders.KEYS, message.getKeys())
//                .setHeader(MessageHeaders.ID, "")
                .build();
        return syncSend(buildTopic(topic, tag), msg);
    }

    /**
     * 发送延迟消息
     *
     * @param topic   目标主题
     * @param message 消息
     * @param tag     标签
     */
    @Override
    public <T extends BaseMessage> boolean sendDelay(String topic, T message, Duration delay, String... tag) {
        Message<T> msg = MessageUtils.buildMessage(message);
        SendResult sendResult = template.syncSendDelayTimeMills(topic, msg, delay.toMillis());
        return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
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
