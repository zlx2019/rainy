package com.zero.rainy.sample.controller;

import com.zero.rainy.core.model.Result;
import com.zero.rainy.limit.LimitType;
import com.zero.rainy.limit.Limiter;
import com.zero.rainy.sample.model.vo.LimitVo;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * 限流测试
 *
 * @author Zero.
 * <p> Created on 2024/12/31 14:20 </p>
 */
@RestController
@RequestMapping("/limit")
public class LimiterController {

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
