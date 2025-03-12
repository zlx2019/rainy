package com.zero.rainy.core.enums;

import com.zero.rainy.core.enums.supers.SuperEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Zero.
 * <p> Created on 2025/3/11 21:51 </p>
 */
@Getter
@AllArgsConstructor
public enum Gender implements SuperEnum<Integer> {
    MAN(0, "男"),
    WOMAN(1, "女")
    ;
    private final Integer value;
    private final String label;
}
