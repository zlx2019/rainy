package com.zero.rainy.api.grpc;

import com.zero.rainy.api.grpc.pb.user.UserServ;
import com.zero.rainy.api.grpc.pb.user.UserServiceGrpc;
import com.zero.rainy.core.constant.ServiceConstant;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * User 服务 GRPC 客户端
 *
 * @author Zero.
 * <p> Created on 2024/9/5 18:18 </p>
 */
@Service
@Slf4j
public class UserGrpcClient {

    /**
     * 注入 User 服务的gRPC客户端
     */
    @GrpcClient(ServiceConstant.USER)
    private UserServiceGrpc.UserServiceBlockingStub userServStub;

    public void sayHello(String username, String password){
        UserServ.UserRequest request = UserServ.UserRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();
        UserServ.UserReply userReply = userServStub.sayHello(request);
        log.info("client username: {}, password: {}", userReply.getUsername(), userReply.getPassword());
    }
}
