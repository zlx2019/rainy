package com.zero.rainy.auth.model.param;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author Zero.
 * <p> Created on 2025/3/6 17:55 </p>
 */
@Data
public class RefreshParam {
    /**
     * 刷新令牌
     */
    @NotBlank
    private String refreshToken;
}
