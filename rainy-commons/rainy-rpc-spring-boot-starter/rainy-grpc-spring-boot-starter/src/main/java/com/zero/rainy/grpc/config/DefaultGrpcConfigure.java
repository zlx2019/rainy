package com.zero.rainy.grpc.config;

import com.zero.rainy.grpc.interceptors.GrpcClientInterceptor;
import com.zero.rainy.grpc.interceptors.GrpcServerInterceptor;
import net.devh.boot.grpc.client.interceptor.GlobalClientInterceptorConfigurer;
import net.devh.boot.grpc.server.interceptor.GlobalServerInterceptorConfigurer;

/**
 * 全局 gRPC 默认配置类
 *
 * @author Zero.
 * <p> Created on 2024/9/5 16:07 </p>
 */
public class DefaultGrpcConfigure {

    /**
     * 注册 gRPC 客户端拦截器
     */
    public GlobalClientInterceptorConfigurer globalClientInterceptorConfigurer(){
        return registry-> registry.add(new GrpcClientInterceptor());
    }

    /**
     * 注册 gRPC 服务端拦截器
     */
    public GlobalServerInterceptorConfigurer globalServerInterceptorConfigurer(){
        return registry-> registry.add(new GrpcServerInterceptor());
    }
}
