package com.zero.rainy.webclient.filters;

import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.holder.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * WebClient 默认全局过滤器，负责传递全局上下文信息
 *
 * @author Zero.
 * <p> Created on 2024/9/13 13:48 </p>
 */
@Slf4j
public class DefaultWebClientFilter implements ExchangeFilterFunction {

    /**
     * @param clientRequest 要发送的HTTP请求
     * @param next          下一个过滤处理函数
     */
    @Override
    public Mono<ClientResponse> filter(ClientRequest clientRequest, ExchangeFunction next) {
        // 对请求信息进行包装
        ClientRequest.Builder nextRequest = ClientRequest.from(clientRequest);
        // 传递 traceID
        Optional.ofNullable(MDC.get(Constant.TRACE_ID_LOG_KEY))
                .filter(StringUtils::isNoneBlank)
                .ifPresent(traceId-> nextRequest.header(Constant.TRACE_ID_HEADER_KEY, traceId));
        // 传递UserID
        Optional.ofNullable(UserContextHolder.getUser())
                .filter(StringUtils::isNoneBlank)
                .ifPresent(userId-> nextRequest.header(Constant.USER_ID_HEADER_KEY, userId));
        return next.exchange(nextRequest.build());
    }
}
