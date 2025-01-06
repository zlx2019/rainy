package com.zero.rainy.sample.controller;

import com.zero.rainy.cache.template.CacheTemplate;
import com.zero.rainy.core.model.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

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



    @GetMapping("/zSet/add")
    public Result<Long> zSetAdd(String key, Integer count) {
        List<DefaultTypedTuple<Object>> list = IntStream.range(0, count)
                .boxed()
                .map(i -> {
                    return new DefaultTypedTuple<Object>(UUID.randomUUID().toString(), i.doubleValue());
                }).toList();
        Long z = cacheTemplate.zAdd(key, list);
        return Result.ok(z);
    }

    @GetMapping("/zSet/pop")
    public Result<Long> zSetPop(String key, Integer threads) {
        List<CompletableFuture<?>> works = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            Integer no = i;
            works.add(CompletableFuture.runAsync(() -> {
                log.info("线程 {} 开始运行...", no);
                List<Object> values = new ArrayList<>(10000);
                for (int j = 0; j < 10000; j++) {
                    values.add(cacheTemplate.zPopMax(key, String.class));
                }
                log.info("线程 {} 运行结束: {}", no, values.size());
            }));
        }
        works.forEach(CompletableFuture::join);
        return Result.ok();
    }

}
