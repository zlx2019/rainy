package com.zero.rainy.dashboard.controller;

import com.zero.rainy.core.model.Result;
import com.zero.rainy.dashboard.service.RocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Zero.
 * <p> Created on 2024/12/27 16:28 </p>
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard/mq")
public class RocketController {
    private final RocketService rocketService;

    @GetMapping("topics")
    public Result<List<String>> findAllTopic(){
        return Result.ok(rocketService.findAllTopic());
    }
}
