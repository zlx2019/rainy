package com.zero.rainy.core.exception;

import lombok.Getter;

/**
 * 请求限流异常
 *
 * @author Zero.
 * <p> Created on 2024/11/27 16:29 </p>
 */
@Getter
public class RequestLimitException extends BusinessException {
    /**
     * 当前请求的轮次
     */
    private final long current;
    /**
     * 请求阈值
     */
    private final long limits;
    public RequestLimitException(final long limits, final long current) {
        this.limits = limits;
        this.current = current;
    }
}
