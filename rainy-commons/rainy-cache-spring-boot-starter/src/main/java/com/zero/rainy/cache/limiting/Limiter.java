package com.zero.rainy.cache.limiting;

import java.lang.annotation.*;

/**
 * 限流注解
 *
 * @author Zero.
 * <p> Created on 2024/11/26 21:42 </p>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Limiter {

    /**
     * 资源标志
     *  - {@link LimitType#IP} 锁定客户端IP
     *  - {@link LimitType#ARGS} 锁定API参数Hash值
     *  - {@link LimitType#TOKEN} 锁定用户Token
     *  - {@link LimitType#KEY_WORD} 锁定该值
     */
    String key() default "";


    /**
     * 窗口阈值，窗口内允许通过的请求数。默认100qps
     */
    long limits() default 100;

    /**
     * 限流窗口大小(毫秒) 默认10s。
     */
    long limitTime() default 10_000;

    /**
     * 限流类型
     * @see LimitType
     */
    LimitType limitType() default LimitType.ARGS;
}
