package com.zero.rainy.core.model.dto;

import com.zero.rainy.core.model.dto.group.Update;
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

    @NotNull(message = "primary key cannot be null", groups = Update.class)
    private Long id;
}
