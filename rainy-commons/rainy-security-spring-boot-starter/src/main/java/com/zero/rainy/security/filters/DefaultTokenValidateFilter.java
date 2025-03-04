package com.zero.rainy.security.filters;

import com.zero.rainy.security.properties.AuthProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Zero.
 * <p> Created on 2025/3/4 14:19 </p>
 */
@Slf4j
public class DefaultTokenValidateFilter extends TokenValidateFilter {
    public DefaultTokenValidateFilter(AuthProperties securityProperties) {
        super(securityProperties);
    }
}
