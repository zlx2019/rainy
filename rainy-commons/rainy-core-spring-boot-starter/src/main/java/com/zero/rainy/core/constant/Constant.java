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
     * SUCCESSFUL
     * 失败的
     */
    int SUCCESS = 0;
    int FAIL = 1;

    /**
     * 默认页码、页容量
     */
    int PAGE = 1;
    int PAGE_SIZE = 20;

    /**
     * 数据表逻辑删除
     */
    int NOT_DELETED = 0;
    int DELETED = 1;

    /**
     * 分隔符
     */
    String DEFAULT_DELIMITER = ",";
    String SPACE = " ";

    /**
     * 用户ID在请求头中的 key
     */
    String USER_ID_HEADER_KEY = "x-userId-header";
    /**
     * 链路追踪ID，在请求头中的Key
     */
    String TRACE_ID_HEADER_KEY = "x-traceId-header";

    /**
     * 链路追踪ID，在消息头中的Key
     */
    String TRACE_ID_MESSAGE_KEY = "LOG_TRACE_ID";

    /**
     * 链路追踪ID 在日志上下文中的key
     */
    String TRACE_ID_LOG_KEY = "traceId";

    /**
     * 调用方服务标识 在请求头中的 Key
     */
    String CALLER_SERVICE_HEADER_KEY = "x-caller-service";
}
