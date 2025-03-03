package com.zero.rainy.core.config;

import com.zero.rainy.core.enums.DynamicConfigKey;
import com.zero.rainy.core.ext.dynamic.DynamicProperties;
import com.zero.rainy.core.ext.dynamic.DynamicPropertiesKeys;
import lombok.Data;

/**
 * @author Zero.
 * <p> Created on 2025/3/3 23:24 </p>
 */
@Data
@DynamicPropertiesKeys(DynamicConfigKey.SAMPLE)
public class SampleDynConfig implements DynamicProperties {
    private String name;
    private int age = 22;
    private String address;
}
