package com.zero.rainy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 模板服务 启动类.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 13:33 </p>
 */
@SpringBootApplication
public class RainySampleApplication {
    private static final Logger log = LoggerFactory.getLogger(RainySampleApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(RainySampleApplication.class, args);
    }
}
