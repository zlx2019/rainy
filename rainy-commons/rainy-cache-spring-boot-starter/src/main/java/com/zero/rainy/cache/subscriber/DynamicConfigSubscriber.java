package com.zero.rainy.cache.subscriber;

import com.zero.rainy.core.entity.Config;
import com.zero.rainy.core.ext.dynamic.DynamicConfig;
import com.zero.rainy.core.ext.dynamic.DynamicConfigConstant;
import com.zero.rainy.core.ext.dynamic.DynamicConfigContext;
import com.zero.rainy.core.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.nio.charset.StandardCharsets;

/**
 * 动态配置订阅者
 *
 * @author Zero.
 * <p> Created on 2024/11/16 21:11 </p>
 */
@Slf4j
@RequiredArgsConstructor
public class DynamicConfigSubscriber implements MessageListener {
    private final RedisSerializer<Object> redisValueSerializer;

    /**
     * 动态配置更新事件
     * 更新现有配置或加载新的配置项
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String messageValue = new String(message.getBody(), StandardCharsets.UTF_8);
        Object value = redisValueSerializer.deserialize(message.getBody());
        if (value instanceof Config entity){
            String configKey = entity.getConfigKey();
            if (DynamicConfigContext.hasRegistry(configKey)){
                // 更新配置
                String configValue = entity.getConfigValue();
                DynamicConfig config = DynamicConfigContext.getConfig(configKey, DynamicConfig.class);
                DynamicConfig newConfig = JsonUtils.unmarshal(configValue, config.getClass());
                log.info("=================== Dynamic Config Modify ======================");
                log.info("Key: {}, [{}] --> [{}]", configKey, config, newConfig);
                log.info("old: {}", configValue);
                log.info("============================================");
                BeanUtils.copyProperties(newConfig, config);
            }else {
                // 加载新的动态配置
                DynamicConfigContext.registryConfig(entity.getConfigKey(), entity.getConfigValue());
            }
        }
    }
}
