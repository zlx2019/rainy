package com.zero.rainy.sample.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 正常并发消费者
 *
 * @author Zero.
 * <p> Created on 2024/9/26 13:40 </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = "normal-topic", consumerGroup = "normal-topic-consumer")
public class NormalTopicListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("normal-topic consumer[1]: {} ", message);
    }
}
