package com.zero.rainy.sample.listener;

import com.zero.rainy.core.pojo.message.TextMessage;
import com.zero.rainy.message.listener.BaseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

/**
 * 文本消息监听器.
 *
 * @author Zero.
 * <p> Created on 2024/9/28 15:12 </p>
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "text-message-topic", consumerGroup = "text-message-topic-group")
public class TextMessageListener extends BaseMessageListener<TextMessage> {

    /**
     * 消息的消费处理函数
     *
     * @param message 发送的消息实体
     * @return 是否消费成功, 未成功则重新投入队列.
     */
    @Override
    protected boolean handler(TextMessage message) throws Exception {
        log.info("text message: {}", message);
        return true;
    }

    @Override
    public void onMessage(MessageExt message) {
        super.delegate(message, TextMessage.class);
    }
}
