package com.zero.rainy.user.controller;

import com.zero.rainy.api.feign.SampleService;
import com.zero.rainy.core.holder.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Zero.
 * <p> Created on 2024/9/3 16:47 </p>
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final SampleService client;

    @GetMapping("/index")
    public ResponseEntity<?> index(){
        log.info("userId: {}", UserContextHolder.getUser());
        Map<String, Object> ping = client.pingSample();
        return ResponseEntity.ok(ping);
    }
}
