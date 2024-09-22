package com.zero.rainy.core.enums.supers;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.zero.rainy.core.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 逻辑删除状态
 *
 * @author Zero.
 * <p> Created on 2024/9/21 09:00 </p>
 */
@Getter
@AllArgsConstructor
public enum LogicStatus {
    NORMAL(Constant.NOT_DELETED, "未删除"),
    DELETED(Constant.DELETED, "已删除"),;

    @EnumValue
    private final int code;
    private final String message;
}
