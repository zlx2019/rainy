package com.zero.rainy.core.model.entity.supers;

import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

/**
 * 支持乐观锁的通用实体
 *
 * @author Zero.
 * <p> Created on 2024/9/21 00:17 </p>
 */
@Getter
@Setter
public class WithLockEntity<T extends Model<?>> extends SuperEntity<T> {

    /**
     * 乐观锁标识
     */
    @Version
    private Integer version;
}
