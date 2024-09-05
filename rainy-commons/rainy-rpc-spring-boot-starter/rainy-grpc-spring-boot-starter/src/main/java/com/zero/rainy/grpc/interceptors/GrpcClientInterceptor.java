package com.zero.rainy.grpc.interceptors;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

/**
 * gRPC 全局客户端拦截器
 * 进行服务调用时，将一些全局上下文信息传递到下游服务
 *
 * @author Zero.
 * <p> Created on 2024/9/5 15:58 </p>
 */
@Slf4j
public class GrpcClientInterceptor implements ClientInterceptor {

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
        return channel.newCall(methodDescriptor, callOptions);
    }
}
