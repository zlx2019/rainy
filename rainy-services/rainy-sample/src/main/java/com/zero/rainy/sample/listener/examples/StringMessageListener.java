//package com.zero.rainy.sample.listener.examples;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Component;
//
///**
// * 字符串消息消费者. 消息类型为最基础的{@link String} 类型
// *
// * @author Zero.
// * <p> Created on 2024/9/28 13:03 </p>
// */
//@Slf4j
//@Component
//@RequiredArgsConstructor
//@RocketMQMessageListener(topic = "base-text-topic", consumerGroup = "base-text-topic-group")
//public class StringMessageListener implements RocketMQListener<String> {
//    @Override
//    public void onMessage(String message) {
//        log.info("listen text message: [{}]", message);
//    }
//}
