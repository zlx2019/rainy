package com.zero.rainy.user.controller;

import com.zero.rainy.core.pojo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 缓存控制器
 *
 * @author Zero.
 * <p> Created on 2024/9/29 14:36 </p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cache")
public class CacheController {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 设置缓存
     * @param key   键
     * @param id    值
     */
    @PutMapping
    public Result<Void> setCache(String key, Long id){
        redisTemplate.opsForValue().set(key, id);
        return Result.ok();
    }

    @GetMapping
    public Result<Long> getCache(String key){
        Long result;
        Object value = redisTemplate.opsForValue().get(key);
        if (value instanceof Number number){
            result = number.longValue();
        }else {
            result = Long.TYPE.cast(value);
        }
        return Result.ok(result);
    }
}
