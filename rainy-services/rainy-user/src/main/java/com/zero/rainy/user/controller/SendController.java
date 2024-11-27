//package com.zero.rainy.user.controller;
//
//import cn.hutool.core.util.RandomUtil;
//import com.zero.rainy.core.pojo.Result;
//import com.zero.rainy.core.pojo.message.TextMessage;
//import com.zero.rainy.core.pojo.message.UserDelayMessage;
//import com.zero.rainy.message.template.MessageTemplate;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.client.producer.SendResult;
//import org.apache.rocketmq.client.producer.SendStatus;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.apache.rocketmq.spring.support.RocketMQHeaders;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
///**
// * @author Zero.
// * <p> Created on 2024/9/26 13:34 </p>
// */
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/send")
//public class SendController {
//    private final RocketMQTemplate rocketMQTemplate;
//    private final MessageTemplate messageTemplate;
//
//
//    /**
//     * 发送普通文本消息.
//     * @param topic     目标主题
//     * @param message   消息内容
//     *
//     */
//    @GetMapping
//    public Result<Boolean> sendMessage(String topic, String message) {
//        SendResult sendResult = rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(message).build());
//        return Result.ok(sendResult.getSendStatus().equals(SendStatus.SEND_OK));
//    }
//
//    /**
//     * 向指定队列发送 {@link TextMessage} 消息
//     */
//    @GetMapping("/text")
//    public Result<Boolean> sendTextMessage(String topic, String message) {
//        TextMessage textMessage = new TextMessage(message);
//        textMessage.setKeys(UUID.randomUUID().toString());
//        textMessage.setSendTime(LocalDateTime.now());
//        SendResult sendResult = rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(textMessage).build());
//        return Result.ok(sendResult.getSendStatus().equals(SendStatus.SEND_OK));
//    }
//
//    /**
//     * 向指定主题 发送 {@link UserDelayMessage} 消息
//     */
//    @GetMapping("/delay/user")
//    public Result<Boolean> sendUserMessage(String topic) {
//        UserDelayMessage userDelayMessage = new UserDelayMessage();
//        userDelayMessage.setUserId(1231312132L);
//        userDelayMessage.setUsername("zero9501@outlook.com");
//        userDelayMessage.setPassword("root@qwedwq");
//        userDelayMessage.setCreateTime(LocalDateTime.now());
//        userDelayMessage.setUpdateTime(LocalDateTime.now());
//        userDelayMessage.setDelay(Duration.ofSeconds(5));
//        log.info("发送成功.");
//        return Result.ok(messageTemplate.sendDelay(topic, userDelayMessage));
//    }
//
//    /**
//     * 向某个队列发送有序性消息
//     * @param topic     队列
//     * @param message   消息
//     */
//    @GetMapping("/orderly")
//    public Result<Boolean> orderly(String topic, String message) {
//        String keys = UUID.randomUUID().toString();
//        String messageId = RandomUtil.randomNumbers(10);
//        System.out.println("keys:" + keys);
//        System.out.println("messageId:" + messageId);
//        Message<String> msg = MessageBuilder.withPayload(message)
//                .setHeader(RocketMQHeaders.KEYS, keys)
//                .setHeader(RocketMQHeaders.MESSAGE_ID, messageId)
//                .build();
//
//        SendResult sendResult = rocketMQTemplate.syncSendOrderly(topic, msg, keys);
//        return Result.ok(sendResult.getSendStatus().equals(SendStatus.SEND_OK));
//    }
//
//}
