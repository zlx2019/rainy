package com.zero.rainy.uaa.service.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zero.rainy.cache.enums.RedisKey;
import com.zero.rainy.cache.template.CacheTemplate;
import com.zero.rainy.cache.utils.RedisHelper;
import com.zero.rainy.core.exception.BusinessException;
import com.zero.rainy.security.constant.SecurityConstants;
import com.zero.rainy.security.helper.TokenHelper;
import com.zero.rainy.security.model.DefaultUserDetails;
import com.zero.rainy.security.properties.SecurityProperties;
import com.zero.rainy.uaa.enums.AuthRedisKeys;
import com.zero.rainy.uaa.enums.AuthResponseCode;
import com.zero.rainy.uaa.model.param.LoginParam;
import com.zero.rainy.uaa.model.param.RefreshParam;
import com.zero.rainy.uaa.model.vo.LoginVo;
import com.zero.rainy.uaa.model.vo.RefreshVo;
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
    private final SecurityProperties properties;
    private final AuthenticationManager authenticationManager;

    @Override
    public LoginVo login(LoginParam loginParam) {
        // find user by username password
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

    @Override
    public RefreshVo refreshToken(RefreshParam param) {
        DecodedJWT jwt = null;
        try {
            jwt = TokenHelper.parseRefreshToken(param.getRefreshToken());
        }catch (TokenExpiredException e){
            throw new BusinessException(AuthResponseCode.REFRESH_TOKEN_EXPIRED);
        }catch (JWTVerificationException e) {
            log.error("[RefreshToken] Invalid token: {} ", e.getMessage());
            throw new BusinessException(AuthResponseCode.REFRESH_TOKEN_INVALID);
        }
        // TODO 校验会话
        Long userId = jwt.getClaim(SecurityConstants.JWT_CLAIM_USER_ID).asLong();
        String username = jwt.getClaim(SecurityConstants.JWT_CLAIM_USER_NAME).asString();
        DefaultUserDetails details = new DefaultUserDetails(userId, username);
        String accessToken = TokenHelper.createAccessToken(details);
        String key = RedisHelper.keyBuild(AuthRedisKeys.USER_AUTH_TOKEN, username, "access-token");
        cacheTemplate.setEx(key, accessToken, properties.getJwt().getAccessTokenTtl());
        return new RefreshVo(accessToken);
    }

    /**
     * Generate refreshToken session
     */
    public void storeRefreshToken(Authentication authentication, LoginVo loginVo) {
        String username = String.valueOf(authentication.getPrincipal());
        RedisKey authKey = AuthRedisKeys.USER_AUTH_TOKEN;
        String accessTokenKey = RedisHelper.keyBuild(authKey, username, "access-token");
        String refreshTokenKey = RedisHelper.keyBuild(authKey, username, "refresh-token");
        cacheTemplate.setEx(accessTokenKey, loginVo.getAccessToken(), properties.getJwt().getAccessTokenTtl());
        cacheTemplate.setEx(refreshTokenKey, loginVo.getRefreshToken(), properties.getJwt().getRefreshTokenTtl());
    }
}
