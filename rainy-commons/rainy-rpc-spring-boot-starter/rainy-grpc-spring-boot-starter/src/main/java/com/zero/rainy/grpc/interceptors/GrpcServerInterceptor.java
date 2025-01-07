package com.zero.rainy.grpc.interceptors;

import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.holder.UserContextHolder;
import io.grpc.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.Optional;

/**
 * gRPC 全局服务端拦截器
 * 拦截所有 gRPC 请求，读取请求中的上下文信息，保存到当前服务上下文中.
 *
 * @author Zero.
 * <p> Created on 2024/9/5 16:01 </p>
 */
public class GrpcServerInterceptor implements ServerInterceptor {

    /* 用户信息 元数据Key  */
    private static final Metadata.Key<String> USER_KEY = Metadata.Key.of(Constant.USER_ID_HEADER_KEY, Metadata.ASCII_STRING_MARSHALLER);
    /* 链路追踪 元数据Key */
    private static final Metadata.Key<String> TRACE_KEY = Metadata.Key.of(Constant.TRACE_ID_HEADER_KEY, Metadata.ASCII_STRING_MARSHALLER);

    /**
     * 处理接收到的请求
     * @param serverCall 调用请求信息
     * @param metadata   请求元数据信息
     * @param next       拦截处理链的下一个节点
     */
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> next) {
        // 用户上下文信息
        Optional.ofNullable(metadata.get(USER_KEY))
                .filter(StringUtils::isNoneBlank)
                .ifPresent(UserContextHolder::setUser);
        // 链路追踪标识
        Optional.ofNullable(metadata.get(TRACE_KEY))
                .filter(StringUtils::isNoneBlank)
                .ifPresent(traceId-> MDC.put(Constant.TRACE_ID_LOG_KEY, traceId));
        // 对调用请求进行包装，注册请求结束回调钩子
        ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT> call = new ForwardingServerCall.SimpleForwardingServerCall<>(serverCall) {
            // 请求执行结束，清理资源
            @Override
            public void close(Status status, Metadata trailers) {
                UserContextHolder.clear();
                MDC.remove(Constant.TRACE_ID_LOG_KEY);
                super.close(status, trailers);
            }
        };
        return next.startCall(call, metadata);
    }
}
