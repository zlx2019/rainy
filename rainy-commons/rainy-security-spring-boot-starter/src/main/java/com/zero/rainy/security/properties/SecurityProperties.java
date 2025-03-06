package com.zero.rainy.security.properties;

import com.zero.rainy.core.enums.DynamicPropertiesKey;
import com.zero.rainy.core.ext.dynamic.DynamicProperties;
import com.zero.rainy.core.ext.dynamic.DynamicPropertiesKeys;
import com.zero.rainy.security.constant.SecurityConstants;
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
public class SecurityProperties implements DynamicProperties {
    /**
     * 白名单
     */
    private List<String> ignoreUrls = List.of(SecurityConstants.AUTH_ENDPOINT);

    /**
     * JWT 令牌配置
     */
    private JwtProperties jwt = new JwtProperties();

    @Data
    public static class JwtProperties {
        private String issuer = "Zero";
        /**
         * 访问令牌秘钥
         */
        private String accessTokenKey = "8jyqbg:5bx}51gmV92%8]w*YLpe]RfK*:Z=FUF=f>qsP38,P~9B5HnYU=b@Jzw._K>f-DVFzj0x7-^2Q-yj@YMN?kPdLPT5b09pa";
        /**
         * 刷新令牌秘钥
         */
        private String refreshTokenKey = "ZDY!jsM_fG+#-T8cGiF8ir}8Nb-5PAMh46RX,VY:)f192womcP8kA]_9CyLT#MPjL7:Mj*EyQQ]f^T]ugc:#dd3w6JB4Acf^?N4:";
        /**
         * 访问令牌有效期
         */
        private Duration accessTokenTtl = Duration.ofMinutes(30);
        /**
         * 刷新令牌有效期
         */
        private Duration refreshTokenTtl = Duration.ofDays(7);
    }
}
