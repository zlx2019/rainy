package com.zero.rainy.core.ext.dynamic;


import com.zero.rainy.core.enums.ConfigType;

/**
 * 动态配置业务实体
 *
 * @author Zero.
 * <p> Created on 2025/3/3 22:57 </p>
 */
public record DynamicPropertiesBo(
        Long id,
        String configKey,
        String configValue,
        ConfigType configType,
        Integer status
) { }
