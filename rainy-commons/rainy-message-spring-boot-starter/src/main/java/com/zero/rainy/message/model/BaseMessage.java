package com.zero.rainy.message.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 消息服务中的基础消息体, 包含最基本的消息特征
 *
 * @author Zero.
 * <p> Created on 2024/9/27 16:09 </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseMessage {

    /**
     * 消息的唯一标识, 由系统生成。
     * 该字段只有消费者可以获得
     */
    @JsonIgnore
    protected String id;

    /**
     * 消息的索引标识, 可通过该标识快速查找消息，由生产者生成.
     */
    @JsonIgnore
    protected String keys;

    /**
     * 消息在业务层面上重复使用的计数
     * TODO fix 设置到消息头，而不是消息内容中.
     */
    @JsonIgnore
    protected int useCount = 1;

    /**
     * 消息发送时间
     */
    @JsonIgnore
    protected LocalDateTime bornTime;

    public void incrCount() {
        this.useCount++;
    }
}
