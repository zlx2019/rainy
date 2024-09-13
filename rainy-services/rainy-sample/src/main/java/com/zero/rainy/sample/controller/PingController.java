package com.zero.rainy.sample.controller;

import com.zero.rainy.api.grpc.UserGrpcClient;
import com.zero.rainy.api.web.UserServiceClient;
import com.zero.rainy.core.pojo.dto.LoginRequestDTO;
import com.zero.rainy.core.pojo.dto.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Sample 服务状态
 *
 * @author Zero.
 * <p> Created on 2024/9/3 17:29 </p>
 */
@Slf4j
@RestController
@RequestMapping("/sample")
@RequiredArgsConstructor
public class PingController {
    private final UserGrpcClient userGrpcClient;
    private final UserServiceClient userServiceClient;
    /**
     * service ping
     */
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("ok");
    }

    /**
     * feign rpc ping
     */
    @GetMapping("/feign")
    public Map<String, Object> pingSample() {
        log.info("ping feign.");
        return Map.of("name", "张三", "age", 19);
    }

    /**
     * 通过 WebClient 进行异步服务通信
     */
    @GetMapping("/webclient")
    public Mono<ResponseEntity<LoginResponseDTO>> login(String username, String password) {
        log.info("webclient ping username: {} password: {}", username, password);
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO().setUsername(username).setPassword(password);
        return userServiceClient
                .login(loginRequestDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * grpc ping
     */
    @GetMapping("/grpc")
    public void grpcCallUser(String username, String password){
        log.info("ping grpc.");
        userGrpcClient.sayHello(username, password);
    }
}
