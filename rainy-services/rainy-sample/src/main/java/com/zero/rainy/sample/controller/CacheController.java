package com.zero.rainy.sample.controller;

import com.zero.rainy.cache.template.CacheTemplate;
import com.zero.rainy.core.model.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 缓存测试
 *
 * @author Zero.
 * <p> Created on 2024/10/2 12:30 </p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cache")
public class CacheController {
    private final CacheTemplate cacheTemplate;
    private AtomicInteger value = new AtomicInteger(0);
    @PutMapping
    public Result<Void> set(String key, Long value) {
        cacheTemplate.set(key, value);
        return Result.ok();
    }

    @GetMapping
    public Result<Object> get(String key) {
        Long value = cacheTemplate.get(key, Long.class);
        System.out.println(value);
        return Result.ok(value);
    }


    @GetMapping("/push")
    public Result<Long> push(String key) {
        Long index = cacheTemplate.lPush(key, value.incrementAndGet());
        return Result.ok(index);
    }


    @GetMapping("/pop")
    public Result<List<Long>> pop(String key, Integer count) {
        List<Long> list = cacheTemplate.rPop(key, count, Long.class);
        return Result.ok(list);
    }

    @GetMapping("/pop/timeout")
    public Result<Long> popTimeout(String key, Integer timeout) {
        Long l = cacheTemplate.brPop(key, Duration.ofSeconds(timeout), Long.class);
        return Result.ok(l);
    }

}
