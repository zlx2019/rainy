package com.zero.rainy.message.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础文本消息
 *
 * @author Zero.
 * <p> Created on 2024/9/27 16:35 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TextMessage extends BaseMessage {
    public TextMessage(String content) {
        this.content = content;
    }

    /**
     * 消息内容
     */
    private String content;
}
