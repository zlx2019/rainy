package com.zero.rainy.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.ott.OneTimeToken;

/**
 * OneTimeToken
 *
 * @author Zero.
 * <p> Created on 2025/3/7 18:03 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTT {
    private String username;
    private String token;
    private long expiresAt;

    public OTT(OneTimeToken token) {
        this.username = token.getUsername();
        this.token = token.getTokenValue();
        this.expiresAt = token.getExpiresAt().toEpochMilli();
    }
}
