package com.zero.rainy.uaa.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 登录成功响应
 *
 * @author Zero.
 * <p> Created on 2025/3/4 13:45 </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    private String accessToken;
}
