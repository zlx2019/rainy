package com.zero.rainy.message.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息队列统计信息
 *
 * @author Zero.
 * <p> Created on 2025/1/7 17:05 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicStatisticsVo {
    /**
     * 主题名称
     */
    private String topicName;

    /**
     * 消息数
     */
    private long messages;
}
