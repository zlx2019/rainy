package com.zero.rainy.core.model.entity.supers;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Zero.
 * <p> Created on 2025/1/11 16:20 </p>
 */
@Getter
@Setter
public class SuperEntityExt<T extends Model<?>> extends SuperEntity<T> {
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 修改者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
}
