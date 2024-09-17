package com.zero.rainy.core.exception;

import com.zero.rainy.core.pojo.ResultCode;
import lombok.Getter;

/**
 * Rainy 系统自定义业务异常
 *
 * @author Zero.
 * <p> Created on 2024/9/16 02:56 </p>
 */
@Getter
public class BusinessException extends RuntimeException {
    private ResultCode code;

    public BusinessException() {
        super();
    }
    public BusinessException(ResultCode code) {
        super();
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
}
