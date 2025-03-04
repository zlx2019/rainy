package com.zero.rainy.core.model.entity;

import com.zero.rainy.core.model.entity.supers.SuperEntityExt;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Zero.
 * <p> Created on 2024/9/20 10:28 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Sample extends SuperEntityExt<Sample> {
    private String name;
    private int age;
}
