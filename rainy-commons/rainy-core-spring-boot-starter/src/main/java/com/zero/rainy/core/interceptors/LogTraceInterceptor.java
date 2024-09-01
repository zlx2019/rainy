package com.zero.rainy.core.interceptors;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.zero.rainy.core.constant.Constant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 链路追踪ID 拦截器
 *
 * @author Zero.
 * <p> Created on 2024/9/1 22:09 </p>
 */
public class LogTraceInterceptor implements HandlerInterceptor {

    /**
     * 获取请求头中的 `traceId`, 添加到MDC上下文中，输出到日志中.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = request.getHeader(Constant.REQUEST_TRACE_KEY);
        if (StrUtil.isNotBlank(traceId)){
            // 设置到日志上下文
            MDC.put(Constant.LOG_TRACE_KEY, traceId);
        }
        MDC.put(Constant.LOG_TRACE_KEY, RandomUtil.randomString(10));
        System.out.println(MDC.get(Constant.LOG_TRACE_KEY));
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
