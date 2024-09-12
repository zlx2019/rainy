package com.zero.rainy.gateway.utils;

import com.zero.rainy.core.entity.abstracts.Result;
import com.zero.rainy.core.utils.JsonUtil;
import com.zero.rainy.gateway.enums.GatewayError;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 请求响应工具类
 *
 * @author Zero.
 * <p> Created on 2024/9/10 13:57 </p>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseUtils {

    /**
     * 将网关响应写回客户端
     * @param response  响应流
     * @param resp      响应状态
     */
    public static Mono<Void> response(ServerHttpResponse response, GatewayError resp) {
        return writerResponse(response, resp.getStatus(), resp.getCode(), resp.getMessage());
    }

    /**
     * 将响应信息写会客户端
     *
     * @param response 响应流
     * @param status   HTTP 状态码
     * @param code     错误码
     * @param message  错误消息
     */
    private static Mono<Void> writerResponse(ServerHttpResponse response, HttpStatus status, int code, String message) {
        response.setStatusCode(status);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Result<?> result = Result.fail(code, message);
        DataBuffer buffer = response.bufferFactory().wrap(JsonUtil.toJson(result).getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }

}
