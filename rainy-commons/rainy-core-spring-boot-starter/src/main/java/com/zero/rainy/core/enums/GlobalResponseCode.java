package com.zero.rainy.core.enums;

import com.zero.rainy.core.model.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 全局业务响应码
 *
 * @author Zero.
 * <p> Created on 2025/1/8 23:06 </p>
 */
@Getter
@AllArgsConstructor
public enum GlobalResponseCode implements ResponseCode {
    SERVER_INTERNAL_ERROR(FAIL, "Internal Server Error", "服务器内部错误", HttpStatus.INTERNAL_SERVER_ERROR)
    ,;
    private final int code;
    private final String message;
    private final String desc;
    private final HttpStatus status;

    GlobalResponseCode(String message, String desc) {
        this(message, desc, HttpStatus.OK);
    }
    GlobalResponseCode(String desc, HttpStatus status) {
        this(FAIL_MSG, desc, status);
    }
    GlobalResponseCode(String message, String desc, HttpStatus status) {
        this(FAIL, message, desc, status);
    }
}
