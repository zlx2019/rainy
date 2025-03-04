package com.zero.rainy.uaa.enums;

import com.zero.rainy.core.model.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Zero.
 * <p> Created on 2025/3/4 17:52 </p>
 */
@Getter
@AllArgsConstructor
public enum AuthResponseCode implements ResponseCode {

    LOGIN_ERROR(20001, "Login fail, please check the username and password.", "登录失败,用户名或密码错误!"),

    ;
    private final int code;
    private final String message;
    private final String desc;
    private final HttpStatus status;

    AuthResponseCode(int code, String message, String desc) {
        this(code, message, desc, HttpStatus.OK);
    }
    AuthResponseCode(String message, String desc) {
        this(message, desc, HttpStatus.OK);
    }
    AuthResponseCode(String desc, HttpStatus status) {
        this(FAIL_MSG, desc, status);
    }
    AuthResponseCode(String message, String desc, HttpStatus status) {
        this(FAIL, message, desc, status);
    }
}
