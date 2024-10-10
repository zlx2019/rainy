package com.zero.rainy.user.pojo.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Zero.
 * <p> Created on 2024/10/5 16:59 </p>
 */
@Data
public class ValidRequest {

    @NotNull(message = "must not be null")
    private Long id;

    @NotBlank(message = "must not be empty")
    private String name;

}
