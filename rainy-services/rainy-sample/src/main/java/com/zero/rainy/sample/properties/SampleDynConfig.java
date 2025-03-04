package com.zero.rainy.sample.properties;

import com.zero.rainy.core.enums.DynamicPropertiesKey;
import com.zero.rainy.core.ext.dynamic.DynamicProperties;
import com.zero.rainy.core.ext.dynamic.DynamicPropertiesKeys;
import lombok.Data;

/**
 * Sample 服务动态配置
 *
 * @author Zero.
 * <p> Created on 2025/3/3 23:24 </p>
 */
@Data
@DynamicPropertiesKeys(DynamicPropertiesKey.SAMPLE)
public class SampleDynConfig implements DynamicProperties {

}
