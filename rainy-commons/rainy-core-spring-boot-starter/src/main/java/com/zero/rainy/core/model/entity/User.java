package com.zero.rainy.core.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zero.rainy.core.model.entity.supers.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * User - 用户表
 *
 * @author Zero
 * <p> Created on 2025-01-03 16:51:50 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
@EqualsAndHashCode(callSuper = true)
public class User extends SuperEntity<User>{
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 余额
     */
    private BigDecimal balance;
}