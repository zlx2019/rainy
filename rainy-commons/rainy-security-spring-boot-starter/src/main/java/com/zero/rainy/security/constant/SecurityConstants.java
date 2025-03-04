package com.zero.rainy.security.constant;

/**
 * Security 常量
 * @author Zero.
 * <p> Created on 2025/3/4 15:38 </p>
 */
public interface SecurityConstants {
    String AUTH_ENDPOINT = "/auth/**";

    String TOKEN_PREFIX = "Bearer ";
    String TOKEN_HEADER = "Authorization";
    String JWT_CLAIM_USER_ID = "userId";
    String JWT_CLAIM_USER_NAME = "userName";
}
