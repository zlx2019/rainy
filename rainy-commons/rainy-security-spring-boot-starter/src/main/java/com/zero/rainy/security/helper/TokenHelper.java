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
import com.zero.rainy.security.properties.SecurityProperties;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
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
    private static final SecurityProperties PROPS = SpringContextUtil.getBean(SecurityProperties.class);
    private static Algorithm ACCESS_TOKEN_ALGORITHM;
    private static JWTVerifier ACCESS_TOKEN_VERIFIER;

    private static Algorithm REFRESH_TOKEN_ALGORITHM;
    private static JWTVerifier REFRESH_TOKEN_VERIFIER;

    @PostConstruct
    public void init() {
        ACCESS_TOKEN_ALGORITHM = Algorithm.HMAC256(PROPS.getJwt().getAccessTokenKey());
        ACCESS_TOKEN_VERIFIER = JWT.require(ACCESS_TOKEN_ALGORITHM)
                .withIssuer(PROPS.getJwt().getIssuer())
                .build();
        REFRESH_TOKEN_ALGORITHM = Algorithm.HMAC256(PROPS.getJwt().getRefreshTokenKey());
        REFRESH_TOKEN_VERIFIER = JWT.require(REFRESH_TOKEN_ALGORITHM)
                .withIssuer(PROPS.getJwt().getIssuer())
                .build();
    }

    /**
     * Create token by userId and userName
     * @param userId   user pf id
     * @param username user of name
     * @param ttl      survival period
     */
    public static String createToken(Long userId, String username, Duration ttl, Algorithm algorithm) {
        Date now = new Date();
        JWTCreator.Builder builder = JWT.create()
                .withSubject(username)
                .withJWTId(UUID.randomUUID().toString())
                .withIssuer(PROPS.getJwt().getIssuer())
                .withIssuedAt(now)
                .withExpiresAt(Date.from(now.toInstant().plus(ttl)))
                .withClaim(SecurityConstants.JWT_CLAIM_USER_ID, userId)
                .withClaim(SecurityConstants.JWT_CLAIM_USER_NAME, username);
        return builder.sign(algorithm);
    }

    /**
     * Create token by {@link Authentication}
     * @param authentication user basic info
     * @return  accessToken
     */
    public static String createAccessToken(Authentication authentication) {
        if (authentication.getPrincipal() instanceof DefaultUserDetails details) {
            return createToken(details.getUserId(), details.getUsername(), PROPS.getJwt().getAccessTokenTtl(), ACCESS_TOKEN_ALGORITHM);
        }
        return null;
    }

    /**
     * Create access token by {@link UserDetails}
     * @param details   user details
     * @return  accessToken
     */
    public static String createAccessToken(UserDetails details){
        if (details instanceof DefaultUserDetails defaultUserDetails) {
            return createToken(defaultUserDetails.getUserId(), defaultUserDetails.getUsername(), PROPS.getJwt().getAccessTokenTtl(), ACCESS_TOKEN_ALGORITHM);
        }
        return null;
    }

    /**
     * Create token by {@link Authentication} and ttl
     *
     * @param authentication user basic info
     * @param ttl   token ttl
     * @return      token
     */
    public static String createAccessToken(Authentication authentication, Duration ttl) {
        if (authentication.getPrincipal() instanceof DefaultUserDetails details) {
            return createToken(details.getUserId(), details.getUsername(), ttl, ACCESS_TOKEN_ALGORITHM);
        }
        return null;
    }

    /**
     * Create refreshToken
     *
     * @param authentication user basic info
     * @return              refreshToken
     */
    public static String createRefreshToken(Authentication authentication) {
        if (authentication.getPrincipal() instanceof DefaultUserDetails details) {
            return createToken(details.getUserId(), details.getUsername(), PROPS.getJwt().getRefreshTokenTtl(), REFRESH_TOKEN_ALGORITHM);
        }
        return null;
    }

    /**
     * Only verify token
     * @param token jwt token
     */
    public static boolean validate(String token) {
        try {
            parseAccessToken(token);
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
        DecodedJWT jwt = parseAccessToken(token);
        String username = jwt.getClaim(SecurityConstants.JWT_CLAIM_USER_NAME).asString();
        return new UsernamePasswordAuthenticationToken(username, null, null);
    }

    /**
     * verify and parser token
     *
     * @param token token
     * @return {@link DecodedJWT}
     */
    public static DecodedJWT parseAccessToken(String token) throws JWTVerificationException {
        return parseToken(token, ACCESS_TOKEN_VERIFIER);
    }

    /**
     * verify and parse refreshToken
     *
     * @param token RefreshToken
     * @return {@link DecodedJWT}
     */
    public static DecodedJWT parseRefreshToken(String token) throws JWTVerificationException {
        return parseToken(token, REFRESH_TOKEN_VERIFIER);
    }

    /**
     * parser token
     */
    private static DecodedJWT parseToken(String token, JWTVerifier verifier) throws JWTVerificationException {
        return verifier.verify(token);
    }

    public static void main(String[] args) {
//        String token = TokenHelper.createToken(21345L, "zero9501", Duration.ofSeconds(30));
//        System.out.println(token);
//        DecodedJWT decodedJWT = parseToken(token);
//        System.out.println(decodedJWT.getPayload());
    }
}
