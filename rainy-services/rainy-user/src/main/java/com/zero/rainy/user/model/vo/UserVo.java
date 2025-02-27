package com.zero.rainy.user.model.vo;

import com.zero.rainy.web.model.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serial;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * User Vo
 *
 * @author Zero
 * <p> Created on 2025-01-03 16:51:50 </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVo extends BaseVo implements Serializable {
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