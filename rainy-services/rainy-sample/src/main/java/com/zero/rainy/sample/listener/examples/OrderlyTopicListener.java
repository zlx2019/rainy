//package com.zero.rainy.sample.listener.examples;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.ConsumeMode;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.springframework.stereotype.Component;
//
///**
// * 消息顺序消费者， 每个消费线程对应一个Queue
// *
// * @author Zero.
// * <p> Created on 2024/9/26 13:40 </p>
// */
//@Slf4j
//@Component
//@RequiredArgsConstructor
//@RocketMQMessageListener(topic = "user-orderly-topic",
//                         consumerGroup = "user-orderly-topic-consumer",
//                         consumeMode = ConsumeMode.ORDERLY)
//public class OrderlyTopicListener implements RocketMQListener<String> {
//
//    @Override
//    public void onMessage(String message) {
//        log.info("user-orderly-topic consumer: {} ", message);
//    }
//}
