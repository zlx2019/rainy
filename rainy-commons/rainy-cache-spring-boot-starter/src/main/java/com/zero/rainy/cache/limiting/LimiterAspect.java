package com.zero.rainy.cache.limiting;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 限流器切片处理
 *
 * @author Zero.
 * <p> Created on 2024/11/27 00:01 </p>
 */
@Slf4j
@Aspect
@Component
public class LimiterAspect {


    @Around("@annotation(limiter)")
    public Object around(ProceedingJoinPoint joinPoint, Limiter limiter) throws Throwable {
        generateKeys(joinPoint, limiter);
        return joinPoint.proceed();
    }

    /**
     * 获取要锁定的资源标识
     */
    private String generateKeys(ProceedingJoinPoint joinPoint, Limiter limiter) {
        switch (limiter.limitType()){
            case ARGS -> {
                // 获取函数全路径名
                MethodSignature signature = (MethodSignature)joinPoint.getSignature();
                Method method = signature.getMethod();
                String methodFullName = method.getDeclaringClass().getName() + ":" + method.getName();
                System.out.println(methodFullName);


                // 获取函数的所有参数
                // TODO 参数可能是对象.
                String argsStr = Arrays.stream(joinPoint.getArgs()).map(String::valueOf).collect(Collectors.joining("-"));
                System.out.println(argsStr);
            }
        }
        LimitType limitType = limiter.limitType();
        return null;
    }
}
