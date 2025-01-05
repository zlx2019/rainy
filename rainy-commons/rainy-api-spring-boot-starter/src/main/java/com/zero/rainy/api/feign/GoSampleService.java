package com.zero.rainy.api.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Zero.
 * <p> Created on 2025/1/5 21:16 </p>
 */
@FeignClient(name = "go-sample-service")
public interface GoSampleService {

    @GetMapping("/ping")
    String ping();
}
