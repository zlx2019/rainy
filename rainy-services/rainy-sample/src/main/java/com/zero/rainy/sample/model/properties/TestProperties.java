package com.zero.rainy.sample.model.properties;

import com.zero.rainy.core.enums.ConfigType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @author Zero.
 * <p> Created on 2025/2/27 23:43 </p>
 */
@Data
@ConfigurationProperties(prefix = "test")
public class TestProperties {
    private String name;
    private Integer age;
    private String address;
    private Map<ConfigType, String> config;
}
