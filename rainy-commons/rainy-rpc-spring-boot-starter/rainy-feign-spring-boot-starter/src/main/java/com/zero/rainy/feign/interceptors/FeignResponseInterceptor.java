package com.zero.rainy.feign.interceptors;

import feign.InvocationContext;
import feign.ResponseInterceptor;

/**
 * Feign 响应拦截器
 *
 * @author Zero.
 * <p> Created on 2024/9/3 16:28 </p>
 */
public class FeignResponseInterceptor implements ResponseInterceptor {


    @Override
    public Object intercept(InvocationContext context, Chain chain) throws Exception {
        return chain.next(context);
    }
}
