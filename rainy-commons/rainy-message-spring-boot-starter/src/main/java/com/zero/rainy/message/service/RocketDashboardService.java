package com.zero.rainy.message.service;

import com.zero.rainy.message.model.vo.TopicStatisticsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.protocol.admin.ConsumeStats;
import org.apache.rocketmq.remoting.protocol.admin.OffsetWrapper;
import org.apache.rocketmq.remoting.protocol.admin.TopicOffset;
import org.apache.rocketmq.remoting.protocol.admin.TopicStatsTable;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * RocketMQ 统计服务
 *
 * @author Zero.
 * <p> Created on 2025/1/7 16:53 </p>
 */
@Slf4j
@RequiredArgsConstructor
public class RocketDashboardService {
    private final DefaultMQAdminExt admin;


    /**
     * 根据 topic 统计出所有消息(已消费的 + 未消费的)
     *
     * @param topic 主题名
     */
    private TopicStatisticsVo countTotalMessages(String topic) throws Exception {
        Objects.requireNonNull(topic, "topic must not be null");
        // 获取主题统计信息
        TopicStatsTable topicStats = admin.examineTopicStats(topic);
        long total = 0;
        // 遍历主题下的所有队列，统计消息量
        for (MessageQueue queue : topicStats.getOffsetTable().keySet()) {
            // MaxOffset 表示队列中最新的消息位置
            // MinOffset 表示队列中最早的消息位置
            // 两者的差值则就是队列实际的消息量(包含已消费的消息和未消费的消息)
            TopicOffset offset = topicStats.getOffsetTable().get(queue);
            total += offset.getMaxOffset() - offset.getMinOffset();
        }
        return new TopicStatisticsVo(topic, total);
    }


    /**
     * 根据 topic 统计出当前未消费的消息数量(当前可以被消费但未被消费的)
     *
     * @param topic 主题名
     * @param consumerGroup 消费组
     */
    private TopicStatisticsVo countUnconsumedMessages(String topic, String consumerGroup) throws Exception {
        Objects.requireNonNull(topic, "topic must not be null");
        Objects.requireNonNull(consumerGroup, "consumerGroup must not be null");
        // 获取主题统计信息
        TopicStatsTable topicStats = admin.examineTopicStats(topic);
        // 获取消费者在该主题下的消费统计信息, 包含目前已消费到的位置
        ConsumeStats consumeStats = admin.examineConsumeStats(consumerGroup, topic);
        long totalUnConsumedMessages = 0;
        // 统计每个队列
        for (MessageQueue queue : topicStats.getOffsetTable().keySet()) {
            // 队列最新消息位置
            long maxOffset = topicStats.getOffsetTable().get(queue).getMaxOffset();
            // 已消费到的位置
            long consumerOffset = consumeStats.getOffsetTable().get(queue).getConsumerOffset();
            // 差值为未消费的消息量
            totalUnConsumedMessages += maxOffset - consumerOffset;
        }
        return new TopicStatisticsVo(topic, totalUnConsumedMessages);
    }

    /**
     * 统计出某个时间范围内所消费的消息量
     *
     * @param topic         主题名
     * @param consumerGroup 消费组
     * @param begin         起始时间
     * @param end           截止时间
     */
    private TopicStatisticsVo countConsumptionMessagesByTimeBetween(String topic, String consumerGroup, Date begin, Date end) throws Exception {
        long totalConsumedMessages = 0;
        // 统计信息
        TopicStatsTable topicStats = admin.examineTopicStats(topic);
        ConsumeStats consumeStats = admin.examineConsumeStats(consumerGroup, topic);
        for (MessageQueue queue : topicStats.getOffsetTable().keySet()) {
            // 获取起始时间和截止时间时的消息位置
            long beginOffset = admin.searchOffset(queue, begin.getTime());
            long endOffset = admin.searchOffset(queue, end.getTime());
            // 消费偏移量
            OffsetWrapper offsetWrapper = consumeStats.getOffsetTable().get(queue);
            if (offsetWrapper != null){
                long consumerOffset = offsetWrapper.getConsumerOffset();
                if (consumerOffset > beginOffset){
                    totalConsumedMessages += Math.min(consumerOffset, endOffset) - beginOffset;
                }
            }
        }
        return new TopicStatisticsVo(topic, totalConsumedMessages);
    }
}
