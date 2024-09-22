//package com.zero.rainy.user.controller;
//
//import com.zero.rainy.api.feign.SampleService;
//import com.zero.rainy.core.holder.UserContextHolder;
//import com.zero.rainy.core.pojo.dto.LoginRequestDTO;
//import com.zero.rainy.core.pojo.dto.LoginResponseDTO;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Mono;
//
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author Zero.
// * <p> Created on 2024/9/3 16:47 </p>
// */
//@Slf4j
//@RestController
//@RequestMapping("/user")
//@RequiredArgsConstructor
//public class UserController {
//    private final SampleService client;
//
//    @GetMapping("/index")
//    public ResponseEntity<?> index(){
//        log.info("userId: {}", UserContextHolder.getUser());
//        Map<String, Object> ping = client.pingSample();
//        return ResponseEntity.ok(ping);
//    }
//
//
//    /**
//     * WebClient 异步接口 - 用户登录.
//     *
//     * @param dto   登录用户信息
//     * @return      用户信息
//     */
//    @PostMapping("/login")
//    public Mono<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) throws InterruptedException {
//        TimeUnit.SECONDS.sleep(30);
//        log.info("user login: {}", dto);
//        LoginResponseDTO responseDTO = new LoginResponseDTO()
//                .setId(UUID.randomUUID().toString())
//                .setAge(19)
//                .setName("zero")
//                .setEmail("zero@zero.com");
//        return Mono.just(responseDTO);
//    }
//}
