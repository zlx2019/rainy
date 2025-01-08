package com.zero.rainy.core.exception;

import com.zero.rainy.core.model.ResponseCode;
import lombok.Getter;

/**
 * 系统业务异常
 *
 * @author Zero.
 * <p> Created on 2024/9/16 02:56 </p>
 */
@Getter
public class BusinessException extends RuntimeException {
    private ResponseCode code;

    public BusinessException() {
        super();
    }
    public BusinessException(ResponseCode code) {
        super(code.getDesc());
        this.code = code;
    }
    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(Exception cause) {
        super(cause);
    }
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    public static BusinessException of(ResponseCode code) {
        return new BusinessException(code);
    }
}
