package com.zero.rainy.message.template;

import com.zero.rainy.message.model.BaseMessage;

/**
 * 消息服务模板接口
 *
 * @author Zero.
 * <p> Created on 2024/9/27 16:03 </p>
 */
public interface MessageTemplate {

    /**
     * 发送同步消息 {@link BaseMessage}
     *
     * @param topic     目标主题
     * @param message   消息
     * @param tag       消息标签
     *
     */
    default <T extends BaseMessage> boolean send(String topic, T message, String... tag){
        throw new UnsupportedOperationException();
    }
}
