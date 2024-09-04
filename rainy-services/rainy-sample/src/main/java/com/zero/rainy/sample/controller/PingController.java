package com.zero.rainy.sample.controller;

import com.zero.rainy.core.holder.UserContextHolder;
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
public class PingController {

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
}
