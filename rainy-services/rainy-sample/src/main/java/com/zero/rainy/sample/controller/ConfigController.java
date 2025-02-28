package com.zero.rainy.sample.controller;

import com.zero.rainy.cache.publisher.DynamicConfigPublisher;
import com.zero.rainy.core.config.GlobalDynamicConfig;
import com.zero.rainy.core.ext.dynamic.DynamicConfig;
import com.zero.rainy.core.ext.dynamic.DynamicConfigContext;
import com.zero.rainy.core.model.Result;
import com.zero.rainy.limit.LimitType;
import com.zero.rainy.limit.Limiter;
import com.zero.rainy.sample.service.IConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;

/**
 * 动态配置控制器
 *
 * @author Zero.
 * <p> Created on 2024/11/16 21:38 </p>
 */
@Slf4j
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigController {
    private final DynamicConfigPublisher publisher;
    private final IConfigService configService;
    private final GlobalDynamicConfig globalDynamicConfig;

    /**
     * 动态配置列表
     */
    @Limiter(limitType = LimitType.ARGS)
    @GetMapping("/list")
    public Result<Set<Object>> list() {
        log.info("list");
        return Result.ok(DynamicConfigContext.getConfigs());
    }

    /**
     * 刷新动态配置
     * @param id 配置ID
     */
    @GetMapping("/refresh/{id}")
    public Result<?> refresh(@PathVariable Long id) {
        return Optional.ofNullable(configService.getById(id))
                .map(config -> {
                    publisher.publish(config);
                    return Result.ok();
                })
                .orElseGet(Result::fail);
    }

    @GetMapping("/global")
    public Result<DynamicConfig> global() {
        return Result.ok(globalDynamicConfig);
    }
}
