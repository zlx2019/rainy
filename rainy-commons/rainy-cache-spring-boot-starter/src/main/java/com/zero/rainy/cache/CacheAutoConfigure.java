package com.zero.rainy.cache;

import com.zero.rainy.cache.config.RedisConfig;
import org.springframework.context.annotation.Import;

/**
 * 缓存组件自动装配器
 *
 * @author Zero.
 * <p> Created on 2024/9/29 16:10 </p>
 */
@Import({RedisConfig.class})
public class CacheAutoConfigure {

}
