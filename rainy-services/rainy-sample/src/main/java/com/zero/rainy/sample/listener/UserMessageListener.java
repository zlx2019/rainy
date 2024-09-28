package com.zero.rainy.sample.listener;

import com.zero.rainy.core.pojo.message.UserMessage;
import com.zero.rainy.message.listener.BaseMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

/**
 * 用户延迟消息监听器.
 *
 * @author Zero.
 * <p> Created on 2024/9/28 14:45 </p>
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "user-message-topic", consumerGroup = "user-message-topic-group")
public class UserMessageListener extends BaseMessageListener<UserMessage> {

    /**
     * 消息的消费处理函数
     *
     * @param message 发送的消息实体
     * @return 是否消费成功, 未成功则重新投入队列.
     */
    @Override
    protected boolean handler(UserMessage message) throws Exception {
        log.info("UserMessageListener receive message: {}", message);
        return false;
    }

    /**
     * 开启消息重试
     */
    @Override
    protected boolean isEnableRetry() {
        return true;
    }

    /**
     * 消息重试次数
     */
    @Override
    protected int getMaxRetryTimes() {
        return 3;
    }

    /**
     * 开启延迟重试
     */
    @Override
    protected boolean isEnableDelay() {
        return true;
    }

    /**
     * 消息重试次数用尽后的兜底处理
     *
     * @param message 重试多次仍然失败的消息.
     */
    @Override
    protected void goalkeeper(UserMessage message) {
        log.error("重试次数用尽... {}", message);
    }

    @Override
    public void onMessage(MessageExt message) {
        super.delegate(message, UserMessage.class);
    }


}
