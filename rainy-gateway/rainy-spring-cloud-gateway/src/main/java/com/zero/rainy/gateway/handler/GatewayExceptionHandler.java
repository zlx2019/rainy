package com.zero.rainy.gateway.handler;

import com.zero.rainy.gateway.enums.GatewayError;
import com.zero.rainy.gateway.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.DisconnectedClientHelper;
import reactor.core.publisher.Mono;

/**
 * 网关统一异常处理.
 *
 * @author Zero.
 * <p> Created on 2024/9/12 10:42 </p>
 */
@Slf4j
@Order(-1)
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {


    /**
     * 统一异常响应处理
     */
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted() && this.isDisconnectedClientError(ex)){
            // 如果响应已提交，或者客户端连接已断开 直接抛出异常信息
            return Mono.error(ex);
        }
        GatewayError busError = GatewayError.SYS_ERROR;
        String reason = ex.getMessage();
        if (ex instanceof ResponseStatusException rse){
            if (rse instanceof NotFoundException){
                // 未找到服务节点 503
                busError = GatewayError.SERVICE_UNAVAILABLE;
            }else if (rse.getStatusCode().isSameCodeAs(HttpStatus.NOT_FOUND)){
                // 未找到资源 404
                busError = GatewayError.NOT_FOUND;
            }else if (rse.getRootCause() instanceof TimeoutException){
                // 连接超时
                busError = GatewayError.TIME_OUT;
            }
            reason = rse.getReason();
        }
        log.error("【网关异常】 Path: {}, reason: {}", exchange.getRequest().getPath(), reason);
        return ResponseUtils.response(response, busError);
    }

    /**
     * 判断客户端连接是否已经断开
     */
    private boolean isDisconnectedClientError(Throwable ex) {
        return DisconnectedClientHelper.isClientDisconnectedException(ex);
    }
}
