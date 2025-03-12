package com.zero.rainy.core.model.entity;

import com.zero.rainy.core.enums.Gender;
import com.zero.rainy.core.model.entity.supers.SuperEntityExt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * User - 用户表
 *
 * @author Zero
 * <p> Created on 2025-01-03 16:51:50 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends SuperEntityExt<User> {
    /**
     * 用户名
     */
    protected String username;
    /**
     * 密码
     */
    protected String password;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 性别
     */
    private Gender gender;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 手机号码
     */
    private String phone;
    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;
}