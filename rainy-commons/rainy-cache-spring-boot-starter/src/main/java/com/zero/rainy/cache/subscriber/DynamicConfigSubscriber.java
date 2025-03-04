package com.zero.rainy.cache.subscriber;

import com.zero.rainy.core.enums.DynamicPropertiesKey;
import com.zero.rainy.core.ext.dynamic.DynamicProperties;
import com.zero.rainy.core.ext.dynamic.DynamicPropertiesContext;
import com.zero.rainy.core.ext.dynamic.DynamicPropertiesKeys;
import com.zero.rainy.core.helper.YamlHelper;
import com.zero.rainy.core.model.entity.Config;
import com.zero.rainy.core.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

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
            DynamicPropertiesKey configKey = entity.getConfigKey();
            if (DynamicPropertiesContext.hasRegistry(configKey)){
                // 更新配置
                String configValue = entity.getConfigValue();
                DynamicProperties properties = DynamicPropertiesContext.getConfig(configKey, DynamicProperties.class);
                String prefix = null;
                DynamicPropertiesKeys annotation = properties.getClass().getAnnotation(DynamicPropertiesKeys.class);
                if (Objects.nonNull(annotation)){
                    prefix = annotation.prefix();
                }
                DynamicProperties newProperties = null;
                switch (entity.getConfigType()) {
                    case JSON -> newProperties = JsonUtils.unmarshal(configValue, properties.getClass());
                    case YAML -> {
                        try {
                            newProperties = YamlHelper.bind(configValue, prefix, properties.getClass());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                log.info("=================== Dynamic Config Modify ======================");
                log.info("Key: {}, [{}] --> [{}]", configKey, properties, newProperties);
                log.info("============================================");
                BeanUtils.copyProperties(newProperties, properties);
            }
        }
    }
}
