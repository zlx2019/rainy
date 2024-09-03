package com.zero.rainy.user.controller;

import cn.hutool.core.util.RandomUtil;
import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.rpc.feign.SampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
        String traceId = RandomUtil.randomString(10);
        MDC.put(Constant.LOG_TRACE_KEY, traceId);
        log.info("user");
        Map<String, Object> ping = client.pingSample();
        return ResponseEntity.ok(ping);
    }
}
