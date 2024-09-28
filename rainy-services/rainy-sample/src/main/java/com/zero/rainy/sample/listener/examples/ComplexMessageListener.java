package com.zero.rainy.sample.listener.examples;

import com.zero.rainy.core.pojo.message.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 复合类型消息监听器 {@link TextMessage}, 由框架内部反序列化.
 *
 * @author Zero.
 * <p> Created on 2024/9/28 13:03 </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = "complex-text-topic", consumerGroup = "complex-text-topic-group")
public class ComplexMessageListener implements RocketMQListener<TextMessage> {
    @Override
    public void onMessage(TextMessage message) {
        log.info("listen complex text message: [{}]", message);
    }
}
