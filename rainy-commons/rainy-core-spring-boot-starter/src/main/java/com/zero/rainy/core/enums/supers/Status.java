package com.zero.rainy.core.enums.supers;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 基础状态
 *
 * @author Zero.
 * <p> Created on 2024/9/20 11:37 </p>
 */
@Getter
@AllArgsConstructor
public enum Status implements SuperEnum<Integer> {
    NORMAL(0, "正常可用"),
    NO_NORMAL(1,"不可用")
    ;

    @EnumValue
    private final Integer value;
    private final String label;
}
