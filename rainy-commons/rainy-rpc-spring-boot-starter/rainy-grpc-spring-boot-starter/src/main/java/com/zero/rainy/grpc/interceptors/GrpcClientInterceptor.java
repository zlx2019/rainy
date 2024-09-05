package com.zero.rainy.grpc.interceptors;

import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.holder.UserContextHolder;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.Optional;

/**
 * gRPC 全局客户端拦截器
 * 进行服务调用时，将一些全局上下文信息传递到下游服务
 *
 * @author Zero.
 * <p> Created on 2024/9/5 15:58 </p>
 */
@Slf4j
public class GrpcClientInterceptor implements ClientInterceptor {

    /* 用户信息 元数据Key  */
    private static final Metadata.Key<String> USER_KEY = Metadata.Key.of(Constant.USER_ID_HEADER_KEY, Metadata.ASCII_STRING_MARSHALLER);
    /* 链路追踪 元数据Key */
    private static final Metadata.Key<String> TRACE_KEY = Metadata.Key.of(Constant.TRACE_ID_HEADER_KEY, Metadata.ASCII_STRING_MARSHALLER);

    /**
     * 处理要发起的请求
     *
     * @param method    要调用的远程方法描述信息
     * @param options   调用选项
     * @param channel   被拦截到的grpc请求通道
     */
    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions options, Channel channel) {
        // 将拦截到的请求进行包装，形成新的请求
        return new ForwardingClientCall.SimpleForwardingClientCall<>(channel.newCall(method, options)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                // 将所需要携带的数据，添加到请求元数据中
                // 传递 User 信息
                Optional.ofNullable(UserContextHolder.getUser())
                        .filter(StringUtils::isNoneBlank)
                        .ifPresent(user-> headers.put(USER_KEY, user));
                // 传递 tradeID
                Optional.ofNullable(MDC.get(Constant.LOG_TRACE_KEY))
                        .filter(StringUtils::isNoneBlank)
                        .ifPresent(trace-> headers.put(TRACE_KEY, trace));

                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {
                    @Override
                    public void onHeaders(Metadata headers) {
                        // 接收服务端响应的额外元信息数据
                        super.onHeaders(headers);
                    }
                }, headers);
            }
        };
    }
}
