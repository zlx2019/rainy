package com.zero.rainy.core.pojo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 模拟登录，服务调用请求实体
 *
 * @author Zero.
 * <p> Created on 2024/9/13 14:18 </p>
 */
@Data
@Accessors(chain = true)
public class LoginRequestDTO {
    private String username;
    private String password;
}
