package com.zero.rainy.gateway.filter;

import cn.hutool.core.util.IdUtil;
import com.zero.rainy.core.constant.Constant;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关过滤器，服务请求全局上下文链路追踪ID生成
 *
 * @author Zero.
 * <p> Created on 2024/9/10 16:21 </p>
 */
@Order(0)
public class RequestContextTraceFilter implements GlobalFilter {

    /**
     * @param exchange 当前请求信息
     * @param chain 过滤器链
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = IdUtil.fastUUID();
        ServerHttpRequest request = exchange.getRequest()
                .mutate()
                .header(Constant.TRACE_ID_HEADER_KEY, traceId)
                .build();
        return chain.filter(exchange.mutate().request(request).build());
    }
}
