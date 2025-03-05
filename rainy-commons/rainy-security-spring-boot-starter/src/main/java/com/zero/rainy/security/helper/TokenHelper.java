package com.zero.rainy.security.helper;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * JWT Token helper
 *
 * @author Zero.
 * <p> Created on 2025/3/4 15:22 </p>
 */
@Slf4j
@Component
public class TokenHelper {
    private static final AuthProperties PROPS = SpringContextUtil.getBean(AuthProperties.class);
    private static Algorithm ALGORITHM;
    private static JWTVerifier VERIFIER;

    @PostConstruct
    public void init() {
        ALGORITHM = Algorithm.HMAC256(PROPS.getJwt().getSecretKey());
        VERIFIER = JWT.require(ALGORITHM)
                .withIssuer(PROPS.getJwt().getIssuer())
                .build();
    }

    /**
     * Create token by userId and userName
     * @param userId   user pf id
     * @param username user of name
     */
    public static String createToken(Long userId, String username) {
        Date now = new Date();
        JWTCreator.Builder builder = JWT.create()
                .withSubject(username)
                .withJWTId(UUID.randomUUID().toString())
                .withIssuer(PROPS.getJwt().getIssuer())
                .withIssuedAt(now)
                .withExpiresAt(Date.from(now.toInstant().plus(PROPS.getJwt().getTtl())))
                .withClaim(SecurityConstants.JWT_CLAIM_USER_ID, userId)
                .withClaim(SecurityConstants.JWT_CLAIM_USER_NAME, username);
        return builder.sign(ALGORITHM);
    }

    /**
     * Create token by {@link Authentication}
     * @param authentication user basic info
     * @return  accessToken
     */
    public static String createToken(Authentication authentication) {
        if (authentication.getPrincipal() instanceof DefaultUserDetails details) {
            return createToken(details.getUserId(), details.getUsername());
        }
        return null;
    }

    /**
     * Only verify token
     * @param token jwt token
     */
    public static boolean validate(String token) {
        try {
            parseToken(token);
            return Boolean.TRUE;
        }catch (JWTVerificationException e) {
            log.error("[Auth] token validated: {}", e.getMessage());
            return Boolean.FALSE;
        }
    }

    /**
     * parser token and convert {@link Authentication}
     * @param token jwt token
     * @return {@link Authentication}
     */
    public static Authentication extractToken(String token) throws JWTVerificationException {
        DecodedJWT jwt = parseToken(token);
        String username = jwt.getClaim(SecurityConstants.JWT_CLAIM_USER_NAME).asString();
        return new UsernamePasswordAuthenticationToken(username, null, null);
    }

    /**
     * verify and parser token
     *
     * @param token token
     * @return {@link DecodedJWT}
     */
    public static DecodedJWT parseToken(String token) throws JWTVerificationException {
        return VERIFIER.verify(token);
    }

    public static void main(String[] args) {
//        String token = TokenHelper.createToken(21345L, "zero9501", Duration.ofSeconds(30));
//        System.out.println(token);
//        DecodedJWT decodedJWT = parseToken(token);
//        System.out.println(decodedJWT.getPayload());
    }
}
