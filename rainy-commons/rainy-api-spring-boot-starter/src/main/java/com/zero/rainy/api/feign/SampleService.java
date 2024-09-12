package com.zero.rainy.api.feign;

import com.zero.rainy.api.feign.fallback.SampleServiceFallbackFactory;
import com.zero.rainy.core.constant.ServiceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * Sample 服务 Feign客户端
 * @author Zero.
 * <p> Created on 2024/9/3 15:30 </p>
 */
@FeignClient(name = ServiceConstant.SAMPLE, fallbackFactory = SampleServiceFallbackFactory.class)
public interface SampleService {

    @GetMapping("/sample/feign")
    Map<String, Object> pingSample();
}
