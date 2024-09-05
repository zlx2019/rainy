package com.zero.rainy.core.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 请求工具类
 *
 * @author Zero.
 * <p> Created on 2024/9/5 22:09 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUtils {

    /**
     * 获取当前环境下的 HTTP Request
     */
    public static HttpServletRequest getRequest() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes requestAttributes){
            return requestAttributes.getRequest();
        }
        return null;
    }
}
