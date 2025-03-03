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
public enum DynamicConfigKey {
    GLOBAL("GLOBAL_CONFIG","全局动态配置"),
    SAMPLE("SAMPLE_CONFIG", "sample 服务动态配置"),
    ;

    @EnumValue
    @JsonValue
    private final String key;
    private final String desc;

    public static DynamicConfigKey from(String key) {
        for (DynamicConfigKey keys : DynamicConfigKey.values()) {
            if (keys.getKey().equalsIgnoreCase(key)) {
                return keys;
            }
        }
        return null;
    }
}
