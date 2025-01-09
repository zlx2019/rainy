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
    // 基础错误响应
    SERVER_INTERNAL_ERROR(FAIL, "unknown error", "服务器内部错误", HttpStatus.INTERNAL_SERVER_ERROR),
    SYSTEM_BUSINESS_ERROR("business error", "", HttpStatus.OK),

    // Web层错误响应
    PARAM_NOT_VALID(10001, "invalid parameters", "缺少必填参数或参数不合法/", HttpStatus.BAD_REQUEST),
    RESOURCE_NOT_FOUND(10004, "Not Found", "资源未找到", HttpStatus.NOT_FOUND),
    METHOD_NOT_SUPPORT(10005, "method not support", "不支持的 Method", HttpStatus.METHOD_NOT_ALLOWED),
    REQUEST_LIMIT(10006, "Too Many Requests", "请求过于频繁", HttpStatus.TOO_MANY_REQUESTS),


    // 数据相关错误
    DATABASE_ERROR(11001, "database error", "数据库错误"),
    RECORD_NOT_FOUND(11004, "record not found", "业务数据未找到"),


    // 详细业务相关错误
    NETWORK_REQUEST_ERROR(12001, "network request error", "内部网络请求错误", HttpStatus.INTERNAL_SERVER_ERROR),




    ;
    private final int code;
    private final String message;
    private final String desc;
    private final HttpStatus status;

    GlobalResponseCode(int code, String message, String desc) {
        this(code, message, desc, HttpStatus.OK);
    }
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
