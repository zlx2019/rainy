package com.zero.rainy.feign.config;

import com.zero.rainy.feign.interceptors.FeignRequestInterceptor;
import com.zero.rainy.feign.interceptors.FeignResponseInterceptor;
import feign.RequestInterceptor;
import feign.ResponseInterceptor;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;

/**
 * 全局 Feign 默认配置类
 *
 * @author Zero.
 * <p> Created on 2024/9/3 16:30 </p>
 */
public class FeignConfigure {
    /**
     * 注入SpringMvc 编码器
     */
    @Bean
    public Encoder feignEncoder(ObjectFactory<HttpMessageConverters> factory) {
        return new SpringFormEncoder(new SpringEncoder(factory));
    }

    /**
     * 注入 SpringMvc 解码器
     */
    @Bean
    public Decoder feignDecoder(ObjectFactory<HttpMessageConverters> factory) {
        return new SpringDecoder(factory);
    }

    /**
     * 关闭 Feign 重试机制
     */
    @Bean
    public Retryer retryer(){
        return Retryer.NEVER_RETRY;
    }

    /**
     * Feign 全局请求拦截器
     */
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignRequestInterceptor();
    }

    /**
     * Feign 全局响应拦截器
     */
    @Bean
    public ResponseInterceptor responseInterceptor(){
        return new FeignResponseInterceptor();
    }
}
