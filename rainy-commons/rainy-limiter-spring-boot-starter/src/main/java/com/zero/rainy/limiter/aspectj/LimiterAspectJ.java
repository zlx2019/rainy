package com.zero.rainy.limiter.aspectj;

import com.zero.rainy.cache.enums.RedisKeys;
import com.zero.rainy.cache.utils.RedisHelper;
import com.zero.rainy.core.enums.GlobalResponseCode;
import com.zero.rainy.core.exception.BusinessException;
import com.zero.rainy.core.holder.UserContextHolder;
import com.zero.rainy.core.utils.HashUtils;
import com.zero.rainy.core.utils.JsonUtils;
import com.zero.rainy.core.utils.RequestUtils;
import com.zero.rainy.limiter.DistributedLimiter;
import com.zero.rainy.limiter.StandaloneLimiter;
import com.zero.rainy.limiter.annotations.ApiLimiter;
import com.zero.rainy.limiter.enums.LimiterRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 限流器切片处理
 *
 * @author Zero.
 * <p> Created on 2024/11/27 00:01 </p>
 */
@Slf4j
@Aspect
@Component
@SuppressWarnings("all")
@RequiredArgsConstructor
public class LimiterAspectJ {
    private final StandaloneLimiter standaloneLimiter;
    private final DistributedLimiter distributedLimiter;
    // 表示限流接口本身
    private final String IDENTITY = "identity";


    /**
     * 前置拦截
     * @param limiter 限流器
     */
    @Before("@annotation(limiter)")
    public void before(JoinPoint joinPoint, ApiLimiter limiter){
        String hashKey = hashKeys(joinPoint, limiter);
        if (StringUtils.isBlank(hashKey)){
            return;
        }
        boolean permit = switch (limiter.mode()){
            case STANDALONE -> standaloneLimiter.tryAcquire(hashKey, limiter);
            case CLUSTER -> distributedLimiter.tryAcquire(RedisHelper.keyBuild(RedisKeys.REQUEST_LIMITER_COUNT, hashKey), limiter);
        };
        if (!permit){
            throw new BusinessException(GlobalResponseCode.REQUEST_LIMIT);
        }
    }

    /**
     * 根据限流类型，生成不同的资源标识
     */
    private String hashKeys(JoinPoint joinPoint, ApiLimiter limiter) {
        StringBuilder sb = new StringBuilder();
        String keyword = switch (limiter.rule()){
            // TODO 序列化 后续优化.
            case ARGS -> HashUtils.hash(JsonUtils.marshal(joinPoint.getArgs()));
            case IP -> RequestUtils.getIpAddr();
            case API -> IDENTITY;
            case TOKEN -> UserContextHolder.getUser();
            case KEY_WORD -> limiter.key();
        };
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        if (limiter.share() && !LimiterRule.ARGS.equals(limiter.rule()) && !LimiterRule.IP.equals(limiter.rule())){
            return sb.append(keyword).toString();
        }
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        String methodFullName = method.getDeclaringClass().getName() + "#" + method.getName();
        return sb.append(methodFullName).append(":").append(keyword).toString();
    }
}
