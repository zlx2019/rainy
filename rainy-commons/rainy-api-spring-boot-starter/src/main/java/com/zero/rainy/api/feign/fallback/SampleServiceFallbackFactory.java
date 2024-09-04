package com.zero.rainy.api.feign.fallback;

import com.zero.rainy.api.feign.SampleService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * {@link SampleService} 客户端降级工厂
 *
 * @author Zero.
 * <p> Created on 2024/9/3 17:38 </p>
 */
@Component
@Slf4j
public class SampleServiceFallbackFactory implements FallbackFactory<SampleService> {

    @Override
    public SampleService create(Throwable cause) {
        return new SampleService() {
            @Override
            public Map<String, Object> pingSample() {
                exceptionHandler("Ping Sample 服务失败", cause);
                return null;
            }

            /**
             * 异常信息输出
             */
            private void exceptionHandler(String message, Throwable e){
                String cause = "未知错误";
                if (e instanceof FeignException.ServiceUnavailable su){
                    cause = "服务不可用 - " + su.getMessage();
                }else if (e instanceof FeignException.NotFound nf){
                    cause = "请求资源不存在" + nf.getMessage();
                }else if (e instanceof TimeoutException){
                    cause = "请求超时";
                }
                log.error("{} cause: {}", message,cause);
            }
        };
    }
}
