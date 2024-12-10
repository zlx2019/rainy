package com.zero.rainy.sample.controller;

import com.zero.rainy.cache.limiting.LimitType;
import com.zero.rainy.cache.limiting.Limiter;
import com.zero.rainy.cache.template.CacheTemplate;
import com.zero.rainy.core.config.GlobalDynamicConfig;
import com.zero.rainy.core.model.entity.Sample;
import com.zero.rainy.core.model.Pages;
import com.zero.rainy.core.model.Result;
import com.zero.rainy.core.model.request.PageableQueryRequest;
import com.zero.rainy.sample.pojo.vo.LimitVo;
import com.zero.rainy.sample.service.ISampleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 *
 *
 * @author Zero.
 * <p> Created on 2024/9/1 22:34 </p>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sample")
public class SampleController {
    private final ISampleService sampleService;
    private final CacheTemplate cacheTemplate;
    private final GlobalDynamicConfig globalDynamicConfig;

    /**
     * 分页查询
     * @param pqr 分页参数
     */
    @GetMapping
    public Result<Void> list(@Valid PageableQueryRequest pqr) {
        return Result.ok();
    }

    /**
     * Post 请求分页查询
     * @param pageableQueryRequest 分页参数
     */
    @PostMapping
    public Result<Void> listAll(@RequestBody @Valid PageableQueryRequest pageableQueryRequest){
        return Result.ok();
    }

    /**
     * 分页查询列表
     * @param query 分页参数
     *
     * @return {@link Sample}
     */
    @GetMapping("/page")
    public Result<Pages<Sample>> page(@Valid PageableQueryRequest query){
        return Result.ok(sampleService.pages(query));
    }

    @Limiter(limitType = LimitType.API, limits = 10, limitTime = 1, timeUnit = TimeUnit.MINUTES)
    @GetMapping("/limit")
    public Result<?> limit(String name, int age){
        return Result.ok();
    }

    @Limiter(limitType = LimitType.KEY_WORD, key = "test", limits = 10, limitTime = 1, timeUnit = TimeUnit.MINUTES)
    @PostMapping("/limit/post")
    public Result<LimitVo> limitPost(@RequestBody LimitVo vo){
        return Result.ok(vo);
    }
}
