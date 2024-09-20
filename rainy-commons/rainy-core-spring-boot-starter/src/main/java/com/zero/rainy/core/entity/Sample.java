package com.zero.rainy.core.entity;

import com.baomidou.mybatisplus.annotation.Version;
import com.zero.rainy.core.entity.supers.SuperEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Zero.
 * <p> Created on 2024/9/20 10:28 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Sample extends SuperEntity<Sample> {
    private String name;
    private int age;
    /**
     * 乐观锁标志
     */
    @Version
    private int version;
}
