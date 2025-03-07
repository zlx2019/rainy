package com.zero.rainy.auth.service.impl;

import com.zero.rainy.auth.enums.AuthRedisKeys;
import com.zero.rainy.cache.enums.RedisKey;
import com.zero.rainy.cache.utils.RedisHelper;
import com.zero.rainy.core.utils.UUIDUtils;
import com.zero.rainy.security.model.OTT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.ott.*;

import java.time.Duration;
import java.time.Instant;

/**
 * Redis OTT Manager
 *
 * @author Zero.
 * <p> Created on 2025/3/7 17:04 </p>
 */
@Slf4j
@SuppressWarnings("all")
@RequiredArgsConstructor
public class RedisOneTimeTokenManager implements OneTimeTokenService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisKey KEY = AuthRedisKeys.USER_AUTH_OTT_TOKEN;

    @Override
    public OneTimeToken generate(GenerateOneTimeTokenRequest request) {
        String token = UUIDUtils.fastUuid();
        String key = RedisHelper.keyBuild(KEY, token);
        Duration expire = KEY.getExpire();
        long expireAt = Instant.now().plus(expire).toEpochMilli();
        OTT ott = new OTT(request.getUsername(), token, Instant.now().plus(expire).toEpochMilli());
        redisTemplate.opsForValue().set(key, ott, expire);
        return new DefaultOneTimeToken(token, request.getUsername(), Instant.ofEpochMilli(expireAt));
    }

    @Override
    public OneTimeToken consume(OneTimeTokenAuthenticationToken authenticationToken) {
        String token = authenticationToken.getTokenValue();
        String key = RedisHelper.keyBuild(AuthRedisKeys.USER_AUTH_OTT_TOKEN, token);
        if (redisTemplate.opsForValue().getAndDelete(key) instanceof OTT ott){
            return new DefaultOneTimeToken(ott.getToken(), ott.getUsername(), Instant.ofEpochMilli(ott.getExpiresAt()));
        }
        return null;
    }
}
