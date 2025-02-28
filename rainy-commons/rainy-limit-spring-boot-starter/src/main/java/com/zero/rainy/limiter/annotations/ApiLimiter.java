package com.zero.rainy.limiter.annotations;

import com.zero.rainy.limiter.enums.LimiterMode;
import com.zero.rainy.limiter.enums.LimiterRule;

import java.lang.annotation.*;
import java.time.temporal.ChronoUnit;

/**
 * 限流器
 *
 * @author Zero.
 * <p> Created on 2024/11/26 21:42 </p>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ApiLimiter {

    /**
     * 资源标志
     *  - {@link LimiterRule#IP} 锁定客户端IP
     *  - {@link LimiterRule#ARGS} 锁定API参数Hash值
     *  - {@link LimiterRule#TOKEN} 锁定用户Token
     *  - {@link LimiterRule#KEY_WORD} 锁定该值
     */
    String key() default "";


    /**
     * 窗口阈值，窗口内允许通过的请求数。默认100qps
     */
    int limits() default 5;

    /**
     * 时间窗口大小
     */
    long timeWindow() default 30;

    /**
     * 时间窗口单位
     */
    ChronoUnit timeUnit() default ChronoUnit.SECONDS;

    /**
     * 限流规则
     * @see LimiterRule
     */
    LimiterRule rule() default LimiterRule.ARGS;

    /**
     * 限流是否共享
     *  - {@link LimiterRule#ARGS} 类型不生效
     */
    boolean share() default false;


    /**
     * 限流模式
     * @see LimiterMode#STANDALONE 单节点
     * @see LimiterMode#CLUSTER 集群节点
     */
    LimiterMode mode() default LimiterMode.STANDALONE;
}
