package com.zero.rainy.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zero.
 * <p> Created on 2024/9/1 22:34 </p>
 */
@RestController
@RequestMapping
@Slf4j
public class SampleController {

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        log.info("ping");
        return ResponseEntity.ok("ok");
    }
}
