package com.zero.rainy.core.constant;

/**
 * 全局常量
 *
 * @author Zero.
 * <p> Created on 2024/9/1 22:14 </p>
 */
public interface Constant {

    /**
     * 基础业务请求响应码
     */
    int SUCCESS = 0;
    int FAIL = 1;

    /**
     * 数据表逻辑删除
     */
    int NOT_DELETED = 0;
    int DELETED = 1;

    /**
     * 用户ID在请求头中的 key
     */
    String USER_ID_HEADER_KEY = "x-userId-header";
    /**
     * 链路追踪ID，在请求头中的Key
     */
    String TRACE_ID_HEADER_KEY = "x-traceId-header";

    /**
     * 链路追踪ID 在日志上下文中的key
     */
    String LOG_TRACE_KEY = "traceId";

    /**
     * 调用方服务标识 在请求头中的 Key
     */
    String CALLER_SERVICE_HEADER_KEY = "x-caller-service";
}
