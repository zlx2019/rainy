package com.zero.rainy.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Zero.
 * <p> Created on 2024/11/6 22:20 </p>
 */
@Getter
@AllArgsConstructor
public enum DynamicConfigKeys {
    SAMPLE("GLOBAL_CONFIG","全局动态配置"),
    ;

    private final String key;
    private final String desc;

    public static DynamicConfigKeys from(String key) {
        for (DynamicConfigKeys keys : DynamicConfigKeys.values()) {
            if (keys.getKey().equalsIgnoreCase(key)) {
                return keys;
            }
        }
        return null;
    }
}
