package com.zero.rainy.user.controller;

import com.zero.rainy.core.model.Result;
import com.zero.rainy.core.model.dto.LoginRequestDTO;
import com.zero.rainy.core.model.dto.LoginResponseDTO;
import com.zero.rainy.user.pojo.request.ValidRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Zero.
 * <p> Created on 2024/9/3 16:47 </p>
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserBarController {
//    private final SampleService client;

//    @GetMapping("/index")
//    public ResponseEntity<?> index(){
//        log.info("userId: {}", UserContextHolder.getUser());
//        Map<String, Object> ping = client.pingSample();
//        return ResponseEntity.ok(ping);
//    }

    @GetMapping("/validate")
    public Result<?> validate(@Valid ValidRequest request){
        return Result.ok(request);
    }


    @GetMapping("/test")
    public Result<?> validate(BigDecimal price){
        return Result.ok(price);
    }

    /**
     * WebClient 异步接口 - 用户登录.
     *
     * @param dto   登录用户信息
     * @return      用户信息
     */
    @PostMapping("/login")
    public Mono<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) throws InterruptedException {
        TimeUnit.SECONDS.sleep(30);
        log.info("user login: {}", dto);
        LoginResponseDTO responseDTO = new LoginResponseDTO()
                .setId(UUID.randomUUID().toString())
                .setAge(19)
                .setName("zero")
                .setEmail("zero@zero.com");
        return Mono.just(responseDTO);
    }
}
