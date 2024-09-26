package com.zero.rainy.sample.listener.supers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

/**
 * @author Zero.
 * <p> Created on 2024/9/26 16:26 </p>
 */
@Slf4j
@RequiredArgsConstructor
public abstract class SuperMQListener <T> implements RocketMQListener<MessageExt> {
    @Autowired
    private RocketMQMessageConverter converter;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    protected abstract boolean handler(T message) throws Exception;

    protected void delegate(MessageExt messageExt, Class<T> clazz) {
        Message<?> message = RocketMQUtil.convertToSpringMessage(messageExt);
        Object value = converter.getMessageConverter().fromMessage(message, clazz);
        if (!clazz.isInstance(value)){
            log.error("类型不正确");
            return;
        }
        T entity = clazz.cast(value);
        try {
            this.handler(entity);
        }catch (Exception e){
            log.error("TODO");
            throw new RuntimeException(e);
        }
    }
}
