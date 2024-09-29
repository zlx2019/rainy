package com.zero.rainy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

/**
 * Rainy 用户服务
 *
 * @author Zero.
 * <p> Created on 2024/9/3 16:46 </p>
 */
//@EnableDiscoveryClient
//@EnableFeignClients
@SpringBootApplication
public class RainyUserApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RainyUserApplication.class, args);
    }
}
