package com.zero.rainy.core.model.entity.supers;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zero.rainy.core.enums.supers.Status;
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
     * 主键ID
     *  - INPUT 需要手动生成ID
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 状态
     *  0: 正常
     *  1: 锁定不可用
     */
    @TableField(fill = FieldFill.INSERT)
    private Status status;

    /**
     * 逻辑删除
     * 0 - false: 未删除
     * 1 - true: 已删除
     */
//    @TableLogic(delval = "true", value = "false")
    @TableLogic
    private Boolean deleted = Boolean.FALSE;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
