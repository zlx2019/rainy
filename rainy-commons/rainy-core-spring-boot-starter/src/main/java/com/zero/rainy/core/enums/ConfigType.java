package com.zero.rainy.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 动态配置内容格式
 *
 * @author Zero.
 * <p> Created on 2025/2/27 23:23 </p>
 */
@Getter
@AllArgsConstructor
public enum ConfigType {
    JSON,
    YAML,;

    public static ConfigType from(int ordinal) {
        for (ConfigType value : values()) {
            if (value.ordinal() == ordinal) {
                return value;
            }
        }
        throw new IllegalArgumentException("invalid ConfigType ordinal: " + ordinal);
    }

    public static ConfigType from(String name) {
        for (ConfigType value : values()) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalArgumentException("invalid ConfigType name: " + name);
    }
}
