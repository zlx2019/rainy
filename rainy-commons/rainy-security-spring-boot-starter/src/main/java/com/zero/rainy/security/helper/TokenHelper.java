package com.zero.rainy.security.helper;

import cn.hutool.core.util.IdUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zero.rainy.core.ext.spring.SpringContextUtil;
import com.zero.rainy.security.constant.SecurityConstants;
import com.zero.rainy.security.model.DefaultUserDetails;
import com.zero.rainy.security.properties.AuthProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

/**
 * Token 工具
 *
 * @author Zero.
 * <p> Created on 2025/3/4 15:22 </p>
 */
@Slf4j
@Component
public class TokenHelper {
    private static final AuthProperties PROPS = SpringContextUtil.getBean(AuthProperties.class);
    /** 加密算法 */
    private static Algorithm ALGORITHM;
    /** 校验器 */
    private static JWTVerifier VERIFIER;

    @PostConstruct
    public void init() {
        ALGORITHM = Algorithm.HMAC256(PROPS.getJwt().getSecretKey());
        VERIFIER = JWT.require(ALGORITHM).build();
    }

    /**
     * 创建 JWT
     * @param userId   用户ID
     * @param username 用户名
     */
    public static String createToken(Long userId, String username, Duration duration) {
        Date now = new Date();
        JWTCreator.Builder builder = JWT.create()
                .withSubject(username)
                .withJWTId(IdUtil.fastUUID())
                .withIssuedAt(now)
                .withExpiresAt(Date.from(now.toInstant().plus(duration)))
                .withClaim(SecurityConstants.JWT_CLAIM_USER_ID, userId)
                .withClaim(SecurityConstants.JWT_CLAIM_USER_NAME, username);
        return builder.sign(ALGORITHM);
    }

    /**
     * 根据用户认证信息，创建Token
     * @param authentication 用户信息
     * @return  accessToken
     */
    public static String createToken(Authentication authentication) {
        if (authentication.getPrincipal() instanceof DefaultUserDetails details) {
            return createToken(details.getUserId(), details.getUsername(), PROPS.getJwt().getTtl());
        }
        return null;
    }

    /**
     * 校验 JWT
     * @param token 令牌
     */
    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析 JWT
     * @param token jwt
     * @return {@link DecodedJWT}
     */
    public static DecodedJWT parseToken(String token) throws JWTVerificationException {
        return VERIFIER.verify(token);
    }

    public static void main(String[] args) {
        String token = TokenHelper.createToken(21345L, "zero9501", Duration.ofSeconds(30));
        System.out.println(token);
        DecodedJWT decodedJWT = parseToken(token);
        System.out.println(decodedJWT.getPayload());
    }
}
