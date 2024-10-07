package com.zero.rainy.core.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.enums.supers.ResultCodes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * 统一响应实体
 *
 * @author Zero.
 * <p> Created on 2024/9/10 14:10 </p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 业务响应码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据（不存在则忽略序列化）
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> Result<T> ok () {
        return ok(null);
    }

    public static <T> Result<T> ok (T data) {
        return of(Constant.SUCCESS, null, data);
    }
    public static <T> Result<T> fail () {
        return of(Constant.FAIL, null, null);
    }
    public static <T> Result<T> fail (String message) {
        return of(Constant.FAIL, message, null);
    }
    public static <T> Result<T> fail (int code, String message) {
        return of(code, message, null);
    }
    public static <T> Result<T> fail (ResultCode resultCode){
        return fail(resultCode.code(), resultCode.message());
    }
    public static <T> Result<T> fail (ResultCodes resultCodes){
        return fail(resultCodes.getCode());
    }
    private static <T> Result<T> of(int code, String message, T data){
        return new Result<>(code, message, data);
    }
}
