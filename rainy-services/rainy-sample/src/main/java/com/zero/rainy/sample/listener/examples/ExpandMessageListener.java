package com.zero.rainy.sample.listener.examples;

import com.zero.rainy.core.pojo.bo.MessageBo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;
import java.util.Map;
import java.util.UUID;

/**
 * 消息所有属性内容
 *
 * @author Zero.
 * <p> Created on 2024/9/26 13:40 </p>
 */

@Slf4j
@Component
@RequiredArgsConstructor
@RocketMQMessageListener(topic = "complex-topic", consumerGroup = "complex-topic-consumer")
public class ExpandMessageListener implements RocketMQListener<MessageExt> {
    /**
     * 消息转换器
     */
    private final RocketMQMessageConverter converter;

    /**
     * 消息消费处理
     * @param ext   消息扩展体
     */
    @Override
    public void onMessage(MessageExt ext) {
        // 从 MessageExt 读取消息信息
        String topic = ext.getTopic();                  // 消息来自于哪个topic主题
        int queueId = ext.getQueueId();                 // 来自于topic中的哪个队列
        int storeSize = ext.getStoreSize();             // 消息在Broker中存储大小
        long queueOffset = ext.getQueueOffset();        // 在队列中的偏移量
        long bornTimestamp = ext.getBornTimestamp();    // 该消息由生产者发送的时间戳
        SocketAddress bornHost = ext.getBornHost();     // 发送该消息的生产者地址
        long storeTimestamp = ext.getStoreTimestamp();  // 消息存储时间
        SocketAddress storeHost = ext.getStoreHost();   // 存储该消息的Broker地址
        long commitLogOffset = ext.getCommitLogOffset();// 在Broker中的偏移量
        int reconsumeTimes = ext.getReconsumeTimes();   // 该消息重试消费次数
        String transactionId = ext.getTransactionId();  // 事务消息ID

        // 重要参数
        String msgId = ext.getMsgId(); // 消息的ID 由MQ自动生成
        String keys = ext.getKeys();   // 消息的Key, 由应用程序添加，用于查询和索引
        String tags = ext.getTags();    // 消息的标签，用于进一步分类和过滤
        Map<String, String> properties = ext.getProperties(); // 消息自定义数据 / 消息头

        // 将 RocketMQ Message 转换为 Spring Message
        Message<?> message = RocketMQUtil.convertToSpringMessage(ext);
        MessageHeaders headers = message.getHeaders();
        UUID id = headers.getId(); // Spring Message 的消息ID (与RocketMQ 无关)
        msgId = headers.get(RocketMQHeaders.PREFIX + RocketMQHeaders.MESSAGE_ID, String.class); // 消息的ID = ext.getMsgId()
        keys = headers.get(RocketMQHeaders.PREFIX + RocketMQHeaders.KEYS, String.class);        // 消息Key = ext.getKeys()
        String messageId = message.getHeaders().get(RocketMQHeaders.MESSAGE_ID, String.class); // 获取自定义的消息ID
        log.info("consumer message ID: [{}] keys: [{}] MyId: [{}]",msgId, keys, messageId);
        // 将消息序列化为指定实体
        if (converter.getMessageConverter().fromMessage(message, MessageBo.class) instanceof MessageBo bo){
            log.info("message body: {}", bo);
        }
    }
}
