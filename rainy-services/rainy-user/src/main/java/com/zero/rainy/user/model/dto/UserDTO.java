package com.zero.rainy.user.model.dto;

import com.zero.rainy.core.model.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

import java.io.Serial;
import java.io.Serializable;

/**
 * User DTO
 *
 * @author Zero
 * <p> Created on 2025-01-03 16:51:50 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO implements Serializable{
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