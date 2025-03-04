package com.zero.rainy.uaa.model.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 登录参数
 *
 * @author Zero.
 * <p> Created on 2025/3/4 13:41 </p>
 */
@Data
public class LoginParam {
    @NotBlank(message = "must not be blank")
    private String account;
    @NotBlank(message = "must not be blank")
    private String password;
}
