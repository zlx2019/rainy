package com.zero.rainy.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

/**
 * 认证配置属性
 *
 * @author Zero.
 * <p> Created on 2025/3/4 14:24 </p>
 */
@Data
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    /**
     * 白名单
     */
    private List<String> ignoreUrls = List.of();

    /**
     * JWT 令牌配置
     */
    private JwtProperties jwt;

    @Data
    public static class JwtProperties {
        /**
         * JWT Secret key
         */
        private String secretKey;
        /**
         * JWT Survival period
         */
        private Duration ttl = Duration.ofMinutes(30);
    }
}
