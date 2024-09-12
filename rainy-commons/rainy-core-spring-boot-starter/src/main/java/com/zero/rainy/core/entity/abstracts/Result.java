package com.zero.rainy.core.entity.abstracts;

import com.zero.rainy.core.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Result<T> implements Serializable {
    private int code;
    private String message;
    private T data;
    public static <T> Result<T> ok(T data) {
        return of(Constant.SUCCESS_CODE, null, data);
    }
    public static <T> Result<T> fail() {
        return of(Constant.FAIL_CODE, null, null);
    }
    public static <T> Result<T> fail(String message) {
        return of(Constant.FAIL_CODE, message, null);
    }
    public static <T> Result<T> fail(int code, String message) {
        return of(code, message, null);
    }
    private static <T> Result<T> of(int code, String message, T data){
        return new Result<>(code, message, data);
    }
}
