package com.zero.rainy.gateway.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;

import java.net.URI;
import java.util.List;

/**
 * 服务路由信息
 *
 * @author Zero.
 * <p> Created on 2025/1/3 16:08 </p>
 */
@Data
public class RouteDTO {
    /**
     * 路由ID
     */
    @NotBlank
    private String routeId;

    /**
     * 路由服务地址
     */
    @NotBlank
    private String uri;

    /**
     * 断言链
     */
    private List<String> predicates;

    /**
     * 过滤链
     */
    private List<String> filters;

    public RouteDefinition intoRoute() {
        RouteDefinition route = new RouteDefinition();
        route.setId(this.getRouteId());
        route.setUri(URI.create(this.getUri()));
        route.setPredicates(this.predicates.stream().map(PredicateDefinition::new).toList());
        route.setFilters(this.filters.stream().map(FilterDefinition::new).toList());
        return route;
    }
}
