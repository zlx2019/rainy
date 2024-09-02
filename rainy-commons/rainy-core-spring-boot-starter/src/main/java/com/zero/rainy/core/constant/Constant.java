package com.zero.rainy.core.constant;

/**
 * 全局常量
 *
 * @author Zero.
 * <p> Created on 2024/9/1 22:14 </p>
 */
public interface Constant {

    /**
     * 用户ID在请求头中的 key
     */
    String USER_ID_HEADER_KEY = "x-userId-header";
    /**
     * 链路追踪ID，在请求头中的Key
     */
    String REQUEST_TRACE_KEY = "x-traceId-header";

    /**
     * 链路追踪ID 在日志上下文中的key
     */
    String LOG_TRACE_KEY = "traceId";
}
