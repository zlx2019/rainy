package com.zero.rainy.grpc.interceptors;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;

/**
 * gRPC 全局服务端拦截器
 * 拦截所有 gRPC 请求，读取请求中的上下文信息，保存到当前服务上下文中.
 *
 * @author Zero.
 * <p> Created on 2024/9/5 16:01 </p>
 */
public class GrpcServerInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        return serverCallHandler.startCall(serverCall, metadata);
    }
}
