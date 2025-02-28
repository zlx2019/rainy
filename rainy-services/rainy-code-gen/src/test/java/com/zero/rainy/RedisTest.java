package com.zero.rainy;

import com.zero.rainy.cache.template.CacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Zero.
 * <p> Created on 2025/1/22 12:58 </p>
 */
@SpringBootTest(classes = RainyCodeGenApplication.class)
public class RedisTest {

    @Autowired
    private CacheTemplate cacheTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

}
