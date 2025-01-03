package com.zero.rainy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 模板服务 启动类.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 13:33 </p>
 */
//@EnableDynamicConfig
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients
public class RainySampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(RainySampleApplication.class, args);
    }
}
