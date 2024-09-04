package com.zero.rainy.sample.controller;

import com.zero.rainy.sample.model.Params;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zero.
 * <p> Created on 2024/9/4 15:25 </p>
 */
@RestController
@RequestMapping("/testing")
@Slf4j
public class TestController {

    @PostMapping
    public Object test(@RequestBody Params params){
        log.info("111");
        return params;
    }
}
