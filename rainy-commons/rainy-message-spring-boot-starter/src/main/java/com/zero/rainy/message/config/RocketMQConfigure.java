package com.zero.rainy.message.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zero.rainy.core.utils.JsonUtils;
import com.zero.rainy.message.template.MessageTemplate;
import com.zero.rainy.message.template.provider.RocketMQProvider;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

/**
 * RocketMQ 全局配置
 *
 * @author Zero.
 * <p> Created on 2024/9/27 23:58 </p>
 */
public class RocketMQConfigure {

    /**
     * 注入消息转换器, 使用自定义的 Jackson 序列化器, 这样就可以进一步控制消息的编解码处理.
     */
    @Bean
    @Primary
    public RocketMQMessageConverter rocketMQMessageConverter() {
        RocketMQMessageConverter converter = new RocketMQMessageConverter();
        if (converter.getMessageConverter() instanceof CompositeMessageConverter compositeMessageConverter){
            for (MessageConverter messageConverterConverter : compositeMessageConverter.getConverters()) {
                if (messageConverterConverter instanceof MappingJackson2MessageConverter jackson2MessageConverter){
                    // 将 RocketMQ 中的 ObjectMapper 替换为自己的 ObjectMapper, 防止反序列化不一致.
                    jackson2MessageConverter.setObjectMapper(JsonUtils.getMapper());
                }
            }
        }
        return converter;
    }

    /**
     * 注入消息服务模板
     * @param template  RocketMQ客户端
     * @return {@link RocketMQProvider}
     */
    @Bean
    public MessageTemplate messageTemplate(RocketMQTemplate template){
        return new RocketMQProvider(template);
    }
}
