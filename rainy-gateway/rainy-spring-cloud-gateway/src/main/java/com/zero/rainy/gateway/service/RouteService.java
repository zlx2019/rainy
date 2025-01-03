package com.zero.rainy.gateway.service;

import com.zero.rainy.gateway.model.dto.RouteDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 路由服务
 *
 * @author Zero.
 * <p> Created on 2025/1/3 15:48 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteDefinitionWriter routeDefinitionWriter;
    private final ApplicationEventPublisher publisher;
    private final RouteDefinitionLocator locator;


    public Flux<RouteDefinition> findAll(){
        return locator.getRouteDefinitions();
    }

    /**
     * 注册路由
     */
    public void registerRoute(RouteDTO dto){
        RouteDefinition route = dto.intoRoute();
        routeDefinitionWriter.save(Mono.just(route)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 删除路由
     * @param routeId 路由ID
     */
    public void deleteRoute(String routeId){
        routeDefinitionWriter.delete(Mono.just(routeId)).subscribe();
        publisher.publishEvent(new RefreshRoutesEvent(this));
    }

    /**
     * 更新路由
     */
    public void updateRoute(RouteDTO dto){
        RouteDefinition route = dto.intoRoute();
        routeDefinitionWriter.delete(Mono.just(route.getId())).subscribe();
        this.registerRoute(dto);
    }
}
