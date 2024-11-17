package com.zero.rainy.core.config;

import com.zero.rainy.core.annotations.EnableDynamicConfiguration;
import com.zero.rainy.core.enums.DynamicConfigKeys;
import com.zero.rainy.core.ext.dynamic.DynamicConfig;
import lombok.Data;

/**
 * Rainy 全局动态配置
 *
 * @author Zero.
 * <p> Created on 2024/11/6 22:55 </p>
 */
@Data
@EnableDynamicConfiguration(DynamicConfigKeys.SAMPLE)
public class GlobalDynamicConfig implements DynamicConfig {
    private String name;
    private int age;
    private String address;
}
