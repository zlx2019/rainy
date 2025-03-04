package com.zero.rainy.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Zero.
 * <p> Created on 2024/11/6 22:20 </p>
 */
@Getter
@AllArgsConstructor
public enum DynamicPropertiesKey {
    GLOBAL("GLOBAL_CONFIG","全局动态配置"),
    SAMPLE("SAMPLE_CONFIG", "sample 服务动态配置"),
    AUTH("AUTH_CONFIG", "认证授权动态配置"),
    ;

    @EnumValue
    @JsonValue
    private final String key;
    private final String desc;

    public static DynamicPropertiesKey from(String key) {
        for (DynamicPropertiesKey keys : DynamicPropertiesKey.values()) {
            if (keys.getKey().equalsIgnoreCase(key)) {
                return keys;
            }
        }
        return null;
    }
}
