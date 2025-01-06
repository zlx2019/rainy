//package com.zero.rainy.sample.controller;
//
//import com.zero.rainy.api.feign.GoSampleService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author Zero.
// * <p> Created on 2025/1/5 21:19 </p>
// */
//@RestController
//@RequestMapping("/go-sample")
//@RequiredArgsConstructor
//public class GoSampleTestController {
//    private final GoSampleService goSampleService;
//
//    @GetMapping("/ping")
//    public String ping() {
//        return goSampleService.ping();
//    }
//}
