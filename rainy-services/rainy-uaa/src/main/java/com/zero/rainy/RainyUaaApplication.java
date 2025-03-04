package com.zero.rainy;

import com.zero.rainy.core.ext.dynamic.EnableDynamicConfigurationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Zero.
 * <p> Created on 2025/3/4 12:56 </p>
 */
@SpringBootApplication
@EnableDynamicConfigurationProperties
public class RainyUaaApplication {
    public static void main(String[] args) {
        SpringApplication.run(RainyUaaApplication.class, args);
    }
}
