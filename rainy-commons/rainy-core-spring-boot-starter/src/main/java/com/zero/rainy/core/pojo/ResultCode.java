package com.zero.rainy.core.pojo;

import com.zero.rainy.core.constant.Constant;

/**
 * 统一响应码抽象类
 *
 * @param code    响应码，一般指业务错误码。
 * @param message 响应消息，业务错误消息。
 *
 * @author Zero.
 * <p> Created on 2024/9/16 02:17 </p>
 */
public record ResultCode(int code, String message) {
    public static ResultCode of(String message){
        return of(Constant.FAILED, message);
    }
    public static ResultCode of(int code, String message) {
        return new ResultCode(code, message);
    }
}
