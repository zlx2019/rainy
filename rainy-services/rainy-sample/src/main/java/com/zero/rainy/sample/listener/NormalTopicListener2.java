package com.zero.rainy.sample.listener;

import com.zero.rainy.core.pojo.bo.MessageBo;
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
@RocketMQMessageListener(topic = "normal-entity-topic", consumerGroup = "normal-entity-topic-consumer")
public class NormalTopicListener2 implements RocketMQListener<MessageBo> {

    @Override
    public void onMessage(MessageBo message) {
        log.info("normal-topic consumer[2]: {} ", message);
    }
}
