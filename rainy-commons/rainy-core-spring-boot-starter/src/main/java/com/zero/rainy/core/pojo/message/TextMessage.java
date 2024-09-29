package com.zero.rainy.core.pojo.message;

import com.zero.rainy.core.pojo.message.supers.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础文本消息
 *
 * @author Zero.
 * <p> Created on 2024/9/27 16:35 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class TextMessage extends BaseMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public TextMessage(String content) {
        this.content = content;
    }

    /**
     * 消息内容
     */
    private String content;
}
