package com.zero.rainy.sample.controller;

import com.zero.rainy.core.model.Result;
import com.zero.rainy.limiter.annotations.ApiLimiter;
import com.zero.rainy.limiter.enums.LimiterRule;
import com.zero.rainy.sample.model.vo.LimitVo;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;

/**
 * 限流测试
 *
 * @author Zero.
 * <p> Created on 2024/12/31 14:20 </p>
 */
@RestController
@RequestMapping("/limit")
public class LimiterController {

    @ApiLimiter(rule = LimiterRule.ARGS, limits = 5)
    @GetMapping("/limit")
    public Result<?> limit(String name, int age){
        return Result.ok();
    }

    @ApiLimiter(rule = LimiterRule.KEY_WORD, key = "test", limits = 10, timeWindow = 1, timeUnit = ChronoUnit.MINUTES)
    @PostMapping("/limit/post")
    public Result<LimitVo> limitPost(@RequestBody LimitVo vo){
        return Result.ok(vo);
    }
}
