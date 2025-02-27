package com.zero.rainy.web.model.dto;

import com.zero.rainy.web.model.group.Update;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * 通用请求参数
 *
 * @author Zero.
 * <p> Created on 2024/12/31 13:14 </p>
 */
@Getter
@Setter
public class BaseDTO {

    /**
     * 业务数据ID
     */
    @NotNull(message = "primary key cannot be null", groups = Update.class)
    private Long id;
}
