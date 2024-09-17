package com.zero.rainy.core.enums.supers;

import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.pojo.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用响应码枚举
 *
 * @author Zero.
 * <p> Created on 2024/9/16 02:27 </p>
 */
@Getter
@AllArgsConstructor
public enum ResultCodes {
    /* 请求业务 */
    Success(ResultCode.of(Constant.SUCCESSFUL, "successful")),

    /* 通用错误响应 */
    Unknown(ResultCode.of("系统错误")),
    Business(ResultCode.of("系统业务错误")),


    /* 非业务错误响应 */
    NotFound(ResultCode.of(10004, "资源找不到")),
    MethodNotSupport(ResultCode.of(10005, "Method 不匹配"))
    ,;
    private final ResultCode code;
}
