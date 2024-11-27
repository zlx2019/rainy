package com.zero.rainy.sample.controller;

import cn.hutool.core.util.RandomUtil;
import com.zero.rainy.sample.model.GMailRequestDTO;
import com.zero.rainy.sample.model.GMailResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zero.
 * <p> Created on 2024/11/7 10:25 </p>
 */
@Slf4j
@RestController
@RequestMapping("/gmail")
public class GMailController {

    @PostMapping("/fetch")
    public GMailResponseVo getGMail(@RequestBody GMailRequestDTO dto){
        log.info("{}", dto);
        int n = RandomUtil.randomInt(0, 3);
        return switch (n){
            case 1 -> GMailResponseVo.waiting();
            case 2 -> GMailResponseVo.death();
            default -> GMailResponseVo.ok();
        };
    }
}
