package com.zero.rainy.core.utils;

import cn.hutool.core.util.StrUtil;
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
    private static final String UNKNOWN_STR = "unknown";

    /**
     * 获取当前环境下的 HTTP Request
     */
    public static HttpServletRequest getRequest() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes requestAttributes){
            return requestAttributes.getRequest();
        }
        return null;
    }

    /**
     * 获取请求的IP
     */
    public static String getIpAddr(){
        return getIpAddr(getRequest());
    }

    /**
     * 获取请求客户端IP地址
     * @param request HTTP请求
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null){
            return UNKNOWN_STR;
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (isEmptyIP(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            if (isEmptyIP(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (isEmptyIP(ip)) {
                    ip = request.getHeader("HTTP_CLIENT_IP");
                    if (isEmptyIP(ip)) {
                        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                        if (isEmptyIP(ip)) {
                            ip = request.getRemoteAddr();
                        }
                    }
                }
            }
        }
        return ip;
    }

    private static boolean isEmptyIP(String ip) {
        if (StrUtil.isEmpty(ip) || UNKNOWN_STR.equalsIgnoreCase(ip)) {
            return true;
        }
        return false;
    }
}
