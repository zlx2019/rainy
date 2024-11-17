package com.zero.rainy.core.exception;

import com.zero.rainy.core.enums.supers.ResponseCodes;
import lombok.Getter;

/**
 * 发送 HTTP 网络请求异常
 *
 * @author Zero.
 * <p> Created on 2024/11/16 12:30 </p>
 */
@Getter
public class NetworkRequestException extends BusinessException {
    private final String uri;
    private final String method;
    private final String message;
    public NetworkRequestException(String uri, String method) {
        super(ResponseCodes.NETWORK_ERROR);
        this.uri = uri;
        this.method = method;
        this.message = null;
    }
    public NetworkRequestException(String uri, String method, Exception e) {
        super(ResponseCodes.NETWORK_ERROR);
        this.uri = uri;
        this.method = method;
        this.message = e.getMessage();
    }
}
