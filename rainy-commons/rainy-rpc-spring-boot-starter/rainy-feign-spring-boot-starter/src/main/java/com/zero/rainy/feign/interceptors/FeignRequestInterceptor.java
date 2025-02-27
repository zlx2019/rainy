package com.zero.rainy.feign.interceptors;

import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.holder.UserContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Optional;

/**
 * Feign 全局请求拦截器
 * 进行服务调用时，将一些全局上下文信息传递到下游服务
 *
 * @author Zero.
 * <p> Created on 2024/9/3 15:59 </p>
 */
public class FeignRequestInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate next) {
        HttpServletRequest curRequest = getRequest();
        if (Objects.isNull(curRequest)){
            return;
        }
        // 传递 UserID
        Optional.ofNullable(UserContextHolder.getUser())
                .filter(StringUtils::isNoneBlank)
                .ifPresent(user-> next.header(Constant.USER_ID_HEADER_KEY, user));
        // 传递 traceId
        Optional.ofNullable(MDC.get(Constant.TRACE_ID_LOG_KEY))
                .filter(StringUtils::isNoneBlank)
                .ifPresent(traceId-> next.header(Constant.TRACE_ID_REQUEST_HEADER_KEY, traceId));
    }

    /**
     * 获取当前请求 Request
     */
    private HttpServletRequest getRequest(){
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes requestAttributes){
            return requestAttributes.getRequest();
        }
        return null;
    }
}
