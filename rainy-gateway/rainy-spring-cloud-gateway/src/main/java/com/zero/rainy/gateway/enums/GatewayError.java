package com.zero.rainy.gateway.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 网关响应码
 *
 * @author Zero.
 * <p> Created on 2024/9/10 15:56 </p>
 */
@AllArgsConstructor
@Getter
public enum GatewayError {
    SYS_ERROR(50000, "【网关】 Sys Error ", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND(50001, "【网关】 Not Found ", HttpStatus.NOT_FOUND),
    SERVICE_UNAVAILABLE(50002, "【网关】服务节点不可用~", HttpStatus.SERVICE_UNAVAILABLE),
    LIMIT(50003, "【网关-限流】系统繁忙，请稍后再试~", HttpStatus.TOO_MANY_REQUESTS),
    FALLBACK(50004, "【网关-熔断】系统繁忙，请稍后再试~", HttpStatus.TOO_EARLY),
    TIME_OUT(50005, "【网关-超时】 Timeout ", HttpStatus.GATEWAY_TIMEOUT),
    ;
    private final int code;
    private final String message;
    private final HttpStatus status;
}
