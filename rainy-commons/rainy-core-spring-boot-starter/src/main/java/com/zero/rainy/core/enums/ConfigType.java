package com.zero.rainy.core.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
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
    JSON(0),
    YAML(1),
    RUST_TOML(2),
    ;
    @EnumValue
    private final int code;
}
