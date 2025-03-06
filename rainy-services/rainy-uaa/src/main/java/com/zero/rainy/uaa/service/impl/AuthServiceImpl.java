package com.zero.rainy.uaa.service.impl;

import com.zero.rainy.cache.enums.RedisKey;
import com.zero.rainy.cache.template.CacheTemplate;
import com.zero.rainy.cache.utils.RedisHelper;
import com.zero.rainy.security.helper.TokenHelper;
import com.zero.rainy.security.model.DefaultUserDetails;
import com.zero.rainy.security.properties.AuthProperties;
import com.zero.rainy.uaa.enums.AuthRedisKeys;
import com.zero.rainy.uaa.model.param.LoginParam;
import com.zero.rainy.uaa.model.vo.LoginVo;
import com.zero.rainy.uaa.service.IAuthService;
import com.zero.rainy.uaa.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author Zero.
 * <p> Created on 2025/3/4 13:40 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final IUserService userService;

    private final CacheTemplate cacheTemplate;
    private final AuthProperties properties;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginVo login(LoginParam loginParam) {
        // find user by username password
        // default from
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginParam.getAccount(), loginParam.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        // create accessToken
        String accessToken = TokenHelper.createAccessToken(authenticate);
        // create refreshToken
        String refreshToken = TokenHelper.createRefreshToken(authenticate);
        LoginVo loginVo = new LoginVo()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setExpiresIn(properties.getJwt().getAccessTokenTtl().toSeconds());
        this.storeRefreshToken(authentication, loginVo);
        return loginVo;
    }

    /**
     * Generate refreshToken session
     */
    public void storeRefreshToken(Authentication authentication, LoginVo loginVo) {
        if (authentication.getPrincipal() instanceof DefaultUserDetails details){
            RedisKey authKey = AuthRedisKeys.USER_AUTH_TOKEN;
            String accessTokenKey = RedisHelper.keyBuild(authKey, details.getUsername(), "access-token");
            String refreshTokenKey = RedisHelper.keyBuild(authKey, details.getUsername(), "refresh-token");
            cacheTemplate.setEx(accessTokenKey, loginVo.getAccessToken(), properties.getJwt().getAccessTokenTtl());
            cacheTemplate.setEx(refreshTokenKey, loginVo.getRefreshToken(), properties.getJwt().getRefreshTokenTtl());
        }
    }
}
