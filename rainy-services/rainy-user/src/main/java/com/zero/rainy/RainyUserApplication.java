package com.zero.rainy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Rainy 用户服务
 *
 * @author Zero.
 * <p> Created on 2024/9/3 16:46 </p>
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class RainyUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(RainyUserApplication.class, args);
    }
}
