//package com.zero.rainy.cache.ext.limiting;
//
//import com.zero.rainy.cache.constant.Scripts;
//import com.zero.rainy.core.exception.RequestLimitException;
//import com.zero.rainy.core.utils.HashUtils;
//import com.zero.rainy.core.utils.JsonUtils;
//import com.zero.rainy.core.utils.RequestUtils;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.script.DefaultRedisScript;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Method;
//import java.util.List;
//
///**
// * 限流器切片处理
// *
// * @author Zero.
// * <p> Created on 2024/11/27 00:01 </p>
// */
//@Slf4j
//@Aspect
//@Component
//@SuppressWarnings("all")
//@RequiredArgsConstructor
//public class LimiterAspectJ {
//    private final RedisTemplate<String, Object> redisTemplate;
//    // 表示限流接口本身
//    private final String IDENTITY = "identity";
//    private final String LIMIT_KEY_PREFIX = "global:api-limit:";
//
//
//    /**
//     * 前置拦截
//     * @param limiter 限流器
//     */
//    @Before("@annotation(limiter)")
//    public void before(JoinPoint joinPoint, Limiter limiter){
//        String limitKey = generateLimitKey(joinPoint, limiter);
//        DefaultRedisScript<Long> script = new DefaultRedisScript<>(Scripts.LIMIT_SCRIPTS, Long.class);
//        long expire = limiter.timeUnit().toMillis(limiter.limitTime());
//        Long currentLimit = redisTemplate.execute(script, List.of(limitKey), 0, expire);
//        if (currentLimit != null && currentLimit > limiter.limits()){
//            throw new RequestLimitException(limiter.limits(), currentLimit);
//        }
//    }
//
//    /**
//     * 根据限流类型，生成不同的资源标识
//     */
//    private String generateLimitKey(JoinPoint joinPoint, Limiter limiter) {
//        StringBuilder sb = new StringBuilder(LIMIT_KEY_PREFIX);
//        String keyword = switch (limiter.limitType()){
//            // TODO 序列化 后续优化.
//            case ARGS -> HashUtils.hash(JsonUtils.marshal(joinPoint.getArgs()));
//            case IP -> RequestUtils.getIpAddr();
//            case API -> IDENTITY;
//            case TOKEN -> "TODO User ID";
//            case KEY_WORD -> limiter.key();
//        };
//        if (limiter.share() && !LimitType.ARGS.equals(limiter.limitType()) && !LimitType.IP.equals(limiter.limitType())){
//            return sb.append(keyword).toString();
//        }
//        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
//        Method method = signature.getMethod();
//        String methodFullName = method.getDeclaringClass().getName() + "#" + method.getName();
//        return sb.append(methodFullName).append(":").append(keyword).toString();
//    }
//}
