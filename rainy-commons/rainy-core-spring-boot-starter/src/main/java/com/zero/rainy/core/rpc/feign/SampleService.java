package com.zero.rainy.core.rpc.feign;

import com.zero.rainy.core.constant.ServiceConstant;
import com.zero.rainy.core.rpc.feign.fallback.SampleServiceFallbackFactory;
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

    @GetMapping("/sample/ping/rpc")
    Map<String, Object> pingSample();
}
