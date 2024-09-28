package com.zero.rainy.message.listener;

import com.zero.rainy.core.pojo.message.BaseMessage;
import com.zero.rainy.core.pojo.message.delay.DelayMessage;
import com.zero.rainy.message.template.MessageTemplate;
import com.zero.rainy.message.template.provider.RocketMQProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.apache.rocketmq.spring.support.RocketMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.messaging.Message;

import java.time.Duration;

/**
 * 基础消息监听器抽象封装
 *
 * @author Zero.
 * <p> Created on 2024/9/27 17:30 </p>
 */
@Slf4j
@ConditionalOnBean(RocketMQProvider.class)
public abstract class BaseMessageListener <T extends BaseMessage> implements RocketMQListener<MessageExt> {
    protected final int MAX_RETRY_TIMES = -1;

    @Autowired
    private MessageTemplate template;
    @Autowired
    private RocketMQMessageConverter converter;

    /**
     * 消息的消费处理函数
     * @param message       发送的消息实体
     * @return              是否消费成功, 未成功则重新投入队列.
     */
    protected abstract boolean handler(T message) throws Exception;

    /**
     * 消息重试次数用尽后的兜底处理
     * @param message   重试多次仍然失败的消息.
     */
    protected void goalkeeper(T message){
    }

//    @Override
//    public void onMessage(MessageExt message) {
//        this.delegate(message, );
//    }

    /**
     * 消息过滤，检查消息是否已被消费过
     * @param message   消息
     * @return
     *      True: 已被消费过,丢弃消息
     *      False: 未消费过
     */
    protected boolean filter(T message) {
        return Boolean.FALSE;
    }

    /**
     * 消息消费过程中发生异常，是否将此异常抛出.
     * @return
     *      True:  抛出异常，由RocketMQ机制自动重试
     *      False: 不抛出异常，表示出现异常也会被ACK
     */
    protected boolean onErrorThrowException() {
        return Boolean.TRUE;
    }

    /**
     * 是否在应用层面启用消息重试机制: 当消息处理函数{@link #handler(T)} 返回 false 或者发生异常时, 是否将消息重新加入队列中.
     */
    protected boolean isEnableRetry(){
        return Boolean.FALSE;
    }

    /**
     * 获取最大可重试次数
     * @return  -1 表示不上限次数，直到被消费成功.
     */
    protected int getMaxRetryTimes(){
        return MAX_RETRY_TIMES;
    }

    /**
     * 重试时是否发送带有延迟的消息.
     */
    protected boolean isEnableDelay(){
        return Boolean.FALSE;
    }

    /**
     * 获取重试延迟时长
     */
    protected Duration getDelayTime(T message){
        if (message instanceof DelayMessage delayMessage){
            return delayMessage.getDelay();
        }
        return Duration.ofSeconds(3);
    }

    /**
     * 业务标识
     */
    protected String getName(){
        return "Unknown";
    }

    /**
     * 消息的调度处理逻辑
     * @param ext 消息整体内容
     */
    protected void delegate(MessageExt ext, Class<T> clazz){
        Message<?> springMessage = RocketMQUtil.convertToSpringMessage(ext);
        Object messageValue = converter.getMessageConverter().
                fromMessage(springMessage, clazz);
        String messageId = ext.getMsgId();
        String keys = ext.getKeys();
        String topic = ext.getTopic();
        if (!clazz.isInstance(messageValue)){
            log.error("TOPIC: [{}] MESSAGE-ID: [{}] KEYS: [{}] cannot be processed cause: message must not convert to [{}]",
                    topic, messageId, keys, clazz.getName());
            return;
        }
        T message = clazz.cast(messageValue);
        if (ObjectUtils.isEmpty(message)){
            // TODO print error log
            return;
        }
        // 重复消费过滤
        if (filter(message)){
            log.debug("MESSAGE-ID: [{}] KEYS: [{}] has been processed.", messageId, keys);
            return;
        }
        if (this.isEnableRetry() && this.getMaxRetryTimes() != MAX_RETRY_TIMES && message.getUseCount() > getMaxRetryTimes()){
            // 超过最大重试次数
            this.goalkeeper(message);
            return;
        }
        try {
            // 处理消息
            long startTime = System.currentTimeMillis();
            boolean businessResult = this.handler(message);
            long costTime = System.currentTimeMillis() - startTime;
            log.info("【{}】 handle message [{}] - [{}] cost: {} ms | {}", this.getName(), message, keys, costTime,
                    businessResult ? "success" : "fail");
            if (!businessResult && isEnableRetry()){
                int maxRetryTimes = this.getMaxRetryTimes();
                if (maxRetryTimes != MAX_RETRY_TIMES && message.getUseCount() >= maxRetryTimes){
                    this.goalkeeper(message);
                }else {
                    reCommitMessage(message);
                }
            }
        }catch (Exception e){
            if (onErrorThrowException()){
                // 抛出异常，由MQ接手重试
                throw new RuntimeException(e);
            }
            if (isEnableRetry()){
                reCommitMessage(message);
            }
        }
    }

    /**
     * 将消息重新添加到队列中
     * @param message   消息
     */
    private void reCommitMessage(T message) {
        RocketMQMessageListener annotation = this.getClass().getAnnotation(RocketMQMessageListener.class);
        if (annotation == null){
            return;
        }
        String topic = annotation.topic();
        String tag = annotation.selectorExpression();
        message.incrCount();
        boolean sendResult;
        try {
            if (this.isEnableDelay() && !this.getDelayTime(message).isZero()){
                sendResult = template.sendDelay(topic, message, getDelayTime(message), tag);
            }else {
                sendResult = template.send(topic, message, tag);
            }
        }catch (Exception e){
            throw new RuntimeException("message recommit fail", e);
        }
        if (!sendResult){
            throw new RuntimeException("message recommit fail KEYS: " + message.getKeys());
        }
    }
}
