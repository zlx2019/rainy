package com.zero.rainy.sample.controller;

import com.zero.rainy.api.grpc.UserGrpcClient;
import com.zero.rainy.core.holder.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Sample 服务状态
 *
 * @author Zero.
 * <p> Created on 2024/9/3 17:29 </p>
 */
@Slf4j
@RestController
@RequestMapping("/sample/ping")
@RequiredArgsConstructor
public class PingController {
    private final UserGrpcClient userGrpcClient;
    /**
     * Ping
     */
    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("ok");
    }

    /**
     * RPC Ping
     */
    @GetMapping("/rpc")
    public Map<String, Object> pingSample() {
        log.info("userId: {}", UserContextHolder.getUser());
        return Map.of("name", "张三", "age", 19);
    }

    @GetMapping("/grpc")
    public void grpcCallUser(String username, String password){
        userGrpcClient.sayHello(username, password);
    }
}
