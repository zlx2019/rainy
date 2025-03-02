package com.zero.rainy.core.config;

import com.zero.rainy.core.ext.dynamic.DynamicPropertiesKeys;
import com.zero.rainy.core.enums.DynamicConfigKey;
import com.zero.rainy.core.ext.dynamic.DynamicProperties;
import lombok.Data;

/**
 * Rainy 全局动态配置
 *
 * @author Zero.
 * <p> Created on 2024/11/6 22:55 </p>
 */
@Data
@DynamicPropertiesKeys(DynamicConfigKey.GLOBAL)
public class GlobalDynamicConfig implements DynamicProperties {
    private String name;
    private int age = 22;
    private String address;
}
