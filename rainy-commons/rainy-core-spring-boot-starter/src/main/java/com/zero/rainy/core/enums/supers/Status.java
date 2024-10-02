package com.zero.rainy.core.enums.supers;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 实体全局状态枚举
 *
 * @author Zero.
 * <p> Created on 2024/9/20 11:37 </p>
 */
@AllArgsConstructor
@Getter
public enum Status {
    NORMAL(0, "正常"),
    LOCKING(1,"锁定")
    ;

    @EnumValue
    @JsonValue
    private final int code;
    private final String message;
}
