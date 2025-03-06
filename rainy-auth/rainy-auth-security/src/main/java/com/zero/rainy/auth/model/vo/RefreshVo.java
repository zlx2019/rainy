package com.zero.rainy.auth.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zero.
 * <p> Created on 2025/3/6 17:55 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshVo {
    /**
     * 访问令牌
     */
    private String accessToken;
}
