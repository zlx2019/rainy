package com.zero.rainy.web.interceptors;

import cn.hutool.core.util.IdUtil;
import com.zero.rainy.core.constant.Constant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

/**
 * 链路追踪拦截器，实现`traceId`的全局传递
 *
 * @author Zero.
 * <p> Created on 2024/9/1 22:09 </p>
 */
@Slf4j
@SuppressWarnings("all")
public class LogTraceInterceptor implements HandlerInterceptor {

    /**
     * 请求前置处理
     * 获取请求头中的 `traceId`, 添加到 日志的 {@link org.slf4j.spi.MDCAdapter} 上下文中
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = request.getHeader(Constant.TRACE_ID_REQUEST_HEADER_KEY);
        if (StringUtils.isBlank(traceId)){
            traceId = IdUtil.fastUUID();
        }
        MDC.put(Constant.TRACE_ID_LOG_KEY, traceId);
        return true;
    }

    /**
     * 将 `traceId` 从日志上下文中移除
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Optional.ofNullable(MDC.get(Constant.TRACE_ID_LOG_KEY))
                .ifPresent(traceId -> {
                    response.setHeader(Constant.TRACE_ID_RESPONSE_HEADER_KEY, traceId);
                    MDC.remove(Constant.TRACE_ID_LOG_KEY);
                });
    }
}
