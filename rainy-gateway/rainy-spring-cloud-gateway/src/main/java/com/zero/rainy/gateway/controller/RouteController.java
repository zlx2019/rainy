package com.zero.rainy.gateway.controller;

import com.zero.rainy.core.model.Result;
import com.zero.rainy.gateway.model.dto.RouteDTO;
import com.zero.rainy.gateway.service.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 动态路由
 *
 * @author Zero.
 * <p> Created on 2025/1/3 16:10 </p>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/route")
public class RouteController {
    private final RouteService routerService;

    /**
     * 获取所有路由信息
     */
    @GetMapping
    public Flux<RouteDefinition> findAllRoutes(){
        return routerService.findAll();
    }

    /**
     * 注册服务路由
     * @param dto   路由信息
     */
    @PostMapping
    public Mono<Result<Void>> register(@RequestBody @Valid RouteDTO dto) {
        routerService.registerRoute(dto);
        return Mono.just(Result.ok());
    }

    /**
     * 更新路由
     * @param dto 路由信息
     */
    @PutMapping
    public Mono<Result<Void>> update(@RequestBody @Valid RouteDTO dto) {
        routerService.updateRoute(dto);
        return Mono.just(Result.ok());
    }

    /**
     * 删除路由
     * @param routeId    路由ID
     */
    @DeleteMapping
    public Mono<Result<Void>> delete(@RequestParam String routeId) {
        routerService.deleteRoute(routeId);
        return Mono.just(Result.ok());
    }
}
