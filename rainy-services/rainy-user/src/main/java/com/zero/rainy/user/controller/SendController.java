package com.zero.rainy.user.controller;

import cn.hutool.core.util.RandomUtil;
import com.zero.rainy.core.pojo.Result;
import com.zero.rainy.core.pojo.bo.MessageBo;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Zero.
 * <p> Created on 2024/9/26 13:34 </p>
 */
@RestController
@RequestMapping("/send")
@RequiredArgsConstructor
public class SendController {
    private final RocketMQTemplate rocketMQTemplate;


    /**
     * 发送同步消息
     */
    @GetMapping
    public Result<Boolean> sendMessage(String topic, String message) {
        SendResult sendResult = rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(message).build());
        return Result.ok(sendResult.getSendStatus().equals(SendStatus.SEND_OK));
    }

    /**
     * 向某个队列发送有序性消息
     * @param topic     队列
     * @param message   消息
     */
    @GetMapping("/orderly")
    public Result<Boolean> orderly(String topic, String message) {
        String keys = UUID.randomUUID().toString();
        String messageId = RandomUtil.randomNumbers(10);
        System.out.println("keys:" + keys);
        System.out.println("messageId:" + messageId);
        Message<String> msg = MessageBuilder.withPayload(message)
                .setHeader(RocketMQHeaders.KEYS, keys)
                .setHeader(RocketMQHeaders.MESSAGE_ID, messageId)
                .build();

        SendResult sendResult = rocketMQTemplate.syncSendOrderly(topic, msg, keys);
        return Result.ok(sendResult.getSendStatus().equals(SendStatus.SEND_OK));
    }

    /**
     * 发送复杂消息
     */
    @GetMapping("/complex/message")
    public Result<Boolean> complexMessage(String topic, String name, Integer age) {
        MessageBo bo = new MessageBo();
        bo.setName(name);
        bo.setAge(age);
        bo.setDate(LocalDateTime.now());
        Message<MessageBo> message = MessageBuilder.withPayload(bo).build();
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message);
        return Result.ok(sendResult.getSendStatus().equals(SendStatus.SEND_OK));
    }
}
