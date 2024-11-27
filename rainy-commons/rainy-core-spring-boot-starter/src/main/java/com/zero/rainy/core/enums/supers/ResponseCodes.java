package com.zero.rainy.core.enums.supers;

import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.pojo.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 通用响应码枚举
 *
 * @author Zero.
 * <p> Created on 2024/9/16 02:27 </p>
 */
@Getter
@AllArgsConstructor
public enum ResponseCodes {
    Success(ResponseCode.of(Constant.SUCCESS, "Successful")),

    /* 基础错误响应 */
    Unknown(ResponseCode.of("Unpredictable error")),
    Business(ResponseCode.of("Business error")),

    /* 非业务错误响应 */
    PARAM_NOT_VALID(of(10001, "请求参数有误")),
    RESOURCE_NOT_FOUND(of(10002, "资源找不到")),
    METHOD_NOT_SUPPORT(of(10003, "Method 不匹配")),
    NETWORK_ERROR(of(10004, "网络错误")),
    CODEC_ERROR(of(10005, "编解码错误")),
    REQUEST_LIMIT(of(10006, "请求过于频繁")),

    /* 认证授权相关 */
    USER_UNAUTHORIZED(of(20001, "用户未登录")),
    USER_NO_PERMISSION(of(20002, "用户没有权限")),

    /* 业务错误响应 */
    BUSINESS_DATA_NOT_FOUND(of(30001, "业务数据未找到")),
    STATUS_NOT_ALLOWED(of(30002, "当前状态不允许的行为")),
    ;
    private final ResponseCode code;

    private static ResponseCode of(int code, String message, HttpStatus httpStatus) {
        return ResponseCode.of(code, message, httpStatus);
    }
    private static ResponseCode of(int code, String message) {
        return of(code, message, HttpStatus.OK);
    }
}
