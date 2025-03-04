package com.zero.rainy.security.constant;

/**
 * Security 常量
 * @author Zero.
 * <p> Created on 2025/3/4 15:38 </p>
 */
public interface SecurityConstants {

    /** 认证相关端点 */
    String AUTH_ENDPOINT = "/auth/**";

    /** 令牌前缀 */
    String TOKEN_PREFIX = "Bearer ";
    /** 令牌头 */
    String TOKEN_HEADER = "Authorization";

    /** 令牌附加信息 */
    String JWT_CLAIM_USER_ID = "userId";
    String JWT_CLAIM_USER_NAME = "userName";
}
