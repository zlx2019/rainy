package com.zero.rainy.core.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Zero.
 * <p> Created on 2024/9/30 12:05 </p>
 */
public enum OrderBy {
    DESC,
    ASC,;

    /**
     * 根据 Name 序列化规则
     */
    @JsonCreator
    public static OrderBy getByName(String name) {
        if (DESC.name().equalsIgnoreCase(name) || "0".equals(name)) {
            return DESC;
        }else if (ASC.name().equalsIgnoreCase(name) || "1".equals(name)) {
            return ASC;
        }
        throw new IllegalArgumentException("Invalid value for OrderBy: " + name);
    }

}
