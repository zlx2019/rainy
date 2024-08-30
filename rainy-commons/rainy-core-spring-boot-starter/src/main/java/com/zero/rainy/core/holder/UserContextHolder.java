package com.zero.rainy.core.holder;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * 全局服务用户信息上下文
 *
 * @author Zero.
 * <p> Created on 2024/8/30 18:50 </p>
 */
public class UserContextHolder {
    private static final ThreadLocal<Long> CONTEXT = new TransmittableThreadLocal<>();

    /**
     * 设置登录用户信息
     */
    public static void setUser(Long userId){
        CONTEXT.set(userId);
    }

    /**
     * 获取用户信息
     */
    public static Long getUser(){
        return CONTEXT.get();
    }

    /**
     * 清除用户信息
     */
    public static void clear(){
        CONTEXT.remove();
    }
}
