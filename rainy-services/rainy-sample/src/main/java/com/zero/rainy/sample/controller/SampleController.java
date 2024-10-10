package com.zero.rainy.sample.controller;

import com.zero.rainy.core.entity.Sample;
import com.zero.rainy.core.pojo.Pages;
import com.zero.rainy.core.pojo.Result;
import com.zero.rainy.core.pojo.rqeuest.PageableQuery;
import com.zero.rainy.sample.service.ISampleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 分页查询
     * @param pageableQuery 分页参数
     */
    @GetMapping
    public Result<Void> list(@Valid PageableQuery pageableQuery) {
        return Result.ok();
    }

    /**
     * Post 请求分页查询
     * @param pageableQuery 分页参数
     */
    @PostMapping
    public Result<Void> listAll(@RequestBody @Valid PageableQuery pageableQuery){
        return Result.ok();
    }

    /**
     * 分页查询列表
     * @param query 分页参数
     *
     * @return {@link Sample}
     */
    @GetMapping("/page")
    public Result<Pages<Sample>> page(@Valid PageableQuery query){
        return Result.ok(sampleService.pages(query));
    }
}
