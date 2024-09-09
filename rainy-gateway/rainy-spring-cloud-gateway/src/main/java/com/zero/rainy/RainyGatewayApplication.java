package com.zero.rainy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 服务网关.
 *
 * @author Zero.
 * <p> Created on 2024/9/9 17:58 </p>
 */
@SpringBootApplication
@EnableDiscoveryClient
public class RainyGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(RainyGatewayApplication.class, args);
    }
}
