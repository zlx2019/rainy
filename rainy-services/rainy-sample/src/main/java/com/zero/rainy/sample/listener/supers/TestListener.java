package com.zero.rainy.sample.listener.supers;

import com.zero.rainy.core.pojo.bo.MessageBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author Zero.
 * <p> Created on 2024/9/26 16:28 </p>
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "test-topic", consumerGroup = "test-topic-consumer")
public class TestListener extends SuperMQListener<MessageBo>  {


    @Override
    protected boolean handler(MessageBo message) throws Exception {
        log.info("message: {}", message);
        return true;
    }

    @Override
    public void onMessage(MessageExt message) {
        super.delegate(message, MessageBo.class);
    }
}
