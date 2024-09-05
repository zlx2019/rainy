package com.zero.rainy.user.provider;

import com.zero.rainy.api.grpc.pb.user.UserServ;
import com.zero.rainy.api.grpc.pb.user.UserServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * User服务 gRPC 提供者
 *
 * @author Zero.
 * <p> Created on 2024/9/5 18:12 </p>
 */
@GrpcService
@Slf4j
public class UserGrpcProvider extends UserServiceGrpc.UserServiceImplBase {
    /**
     * gRPC 测试接口
     *
     * @param request 请求参数
     * @param response 响应流
     */
    @Override
    public void sayHello(UserServ.UserRequest request, StreamObserver<UserServ.UserReply> response) {
        log.info("sayHello username: {}, password: {}", request.getUsername(), request.getPassword());
        UserServ.UserReply reply = UserServ.UserReply.newBuilder()
                .setUsername("root")
                .setPassword("root@admin")
                .build();
        response.onNext(reply);
        response.onCompleted();
    }
}
