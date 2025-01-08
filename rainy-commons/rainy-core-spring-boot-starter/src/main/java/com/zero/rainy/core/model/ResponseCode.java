package com.zero.rainy.core.model;

import org.springframework.http.HttpStatus;

/**
 * 统一响应码接口: [业务响应码 | 响应消息 | HTTP状态码]
 * @author Zero.
 * <p> Created on 2025/1/8 20:17 </p>
 */
public interface ResponseCode {
    int SUCCESS = 0;
    String SUCCESS_MSG = "success";
    int FAIL = 1;
    String FAIL_MSG = "failed";

    /**
     * 业务响应码/错误码
     */
    default int getCode(){
        return FAIL;
    }
    /**
     * 响应消息
     */
    default String getMessage(){
        return FAIL_MSG;
    }
    /**
     * 描述
     */
    default String getDesc(){
        return "";
    }
    /**
     * HTTP 状态码
     */
    default HttpStatus getStatus(){
        return HttpStatus.OK;
    }
}
