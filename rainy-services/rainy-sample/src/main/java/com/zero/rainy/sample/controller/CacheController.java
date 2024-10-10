package com.zero.rainy.sample.controller;

import com.zero.rainy.cache.template.CacheTemplate;
import com.zero.rainy.core.pojo.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
