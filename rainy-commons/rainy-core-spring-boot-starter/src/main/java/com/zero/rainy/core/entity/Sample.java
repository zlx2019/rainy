package com.zero.rainy.core.entity;

import com.zero.rainy.core.entity.supers.WithLockEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Zero.
 * <p> Created on 2024/9/20 10:28 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Sample extends WithLockEntity<Sample> {
    private String name;
    private int age;
}
