package com.zero.rainy.uaa.service.impl;

import com.zero.rainy.security.helper.TokenHelper;
import com.zero.rainy.security.properties.SecurityProperties;
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
    private final AuthenticationManager authenticationManager;
    private final SecurityProperties securityProperties;

    @Override
    public LoginVo login(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        // find user by username password
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(account, password);
        Authentication authenticate = authenticationManager.authenticate(authentication);
        // create token
        String token = TokenHelper.createToken(authenticate, securityProperties.getJwt().getTtl());
        return new LoginVo(token);
    }
}
