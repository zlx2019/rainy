package com.zero.rainy.core.interceptors;

import cn.hutool.core.util.StrUtil;
import com.zero.rainy.core.constant.Constant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 链路追踪拦截器，实现`traceId`的全局传递
 *
 * @author Zero.
 * <p> Created on 2024/9/1 22:09 </p>
 */
@Slf4j
public class LogTraceInterceptor implements HandlerInterceptor {

    /**
     * 请求前置处理
     * 获取请求头中的 `traceId`, 添加到 日志的 {@link org.slf4j.spi.MDCAdapter} 上下文中
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String traceId = request.getHeader(Constant.TRACE_ID_HEADER_KEY);
        if (StrUtil.isNotBlank(traceId)){
            // 设置到日志上下文
            MDC.put(Constant.LOG_TRACE_KEY, traceId);
        }
        return true;
    }

    /**
     * 将 `traceId` 从日志上下文中移除
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.remove(Constant.LOG_TRACE_KEY);
    }
}
