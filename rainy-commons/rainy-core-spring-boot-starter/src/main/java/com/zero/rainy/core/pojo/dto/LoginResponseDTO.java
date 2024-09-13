package com.zero.rainy.core.pojo.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 模拟登录，服务调用响应实体
 *
 * @author Zero.
 * <p> Created on 2024/9/13 14:21 </p>
 */
@Data
@Accessors(chain = true)
public class LoginResponseDTO {
    private String id;
    private String name;
    private Integer age;
    private String email;
}
