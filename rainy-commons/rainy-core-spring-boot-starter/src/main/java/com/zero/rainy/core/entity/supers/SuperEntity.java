package com.zero.rainy.core.entity.supers;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zero.rainy.core.enums.supers.EntityStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据访问层公共基类实体
 *
 * @author Zero.
 * <p> Created on 2024/8/28 16:51 </p>
 */
@Getter
@Setter
public class SuperEntity <T extends Model<?>> extends Model<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 状态
     */
    private EntityStatus status = EntityStatus.NORMAL;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean deleted;
}
