package com.zero.rainy.cache.publisher;

import com.zero.rainy.core.entity.Config;
import com.zero.rainy.core.ext.dynamic.DynamicConfigConstant;
import io.lettuce.core.dynamic.annotation.CommandNaming;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 动态配置发布者
 *
 * @author Zero.
 * <p> Created on 2024/11/16 21:32 </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicConfigPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 发布事件, 更新动态配置
     * @param config 要更新的动态配置
     */
    public void publish(Config config){
        redisTemplate.convertAndSend(DynamicConfigConstant.EVENT_BUS_TOPIC, config);
    }
}
