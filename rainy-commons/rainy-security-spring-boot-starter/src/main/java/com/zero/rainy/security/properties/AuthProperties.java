package com.zero.rainy.security.properties;

import com.zero.rainy.core.enums.DynamicPropertiesKey;
import com.zero.rainy.core.ext.dynamic.DynamicProperties;
import com.zero.rainy.core.ext.dynamic.DynamicPropertiesKeys;
import lombok.Data;

import java.time.Duration;
import java.util.List;

/**
 * 认证动态配置
 *
 * @author Zero.
 * <p> Created on 2025/3/4 14:24 </p>
 */
@Data
@DynamicPropertiesKeys(value = DynamicPropertiesKey.AUTH,prefix = "security")
public class AuthProperties implements DynamicProperties {
    /**
     * 白名单
     */
    private List<String> ignoreUrls = List.of("/auth/**");

    /**
     * JWT 令牌配置
     */
    private JwtProperties jwt = new JwtProperties();

    @Data
    public static class JwtProperties {
        private String issuer = "Rainy-Zero";
        /**
         * JWT Secret key
         */
        private String secretKey = "";
        /**
         * JWT Survival period
         */
        private Duration ttl = Duration.ofMinutes(30);
    }
}
