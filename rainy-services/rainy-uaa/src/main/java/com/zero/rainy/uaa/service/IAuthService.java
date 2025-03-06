package com.zero.rainy.uaa.service;

import com.zero.rainy.uaa.model.param.LoginParam;
import com.zero.rainy.uaa.model.param.RefreshParam;
import com.zero.rainy.uaa.model.vo.LoginVo;
import com.zero.rainy.uaa.model.vo.RefreshVo;
import jakarta.validation.Valid;

/**
 * 用户认证
 *
 * @author Zero.
 * <p> Created on 2025/3/4 13:40 </p>
 */
public interface IAuthService {
    /**
     * user login
     * @param loginParam {@link LoginParam}
     * @return {@link LoginVo}
     */
    LoginVo login(LoginParam loginParam);


    /**
     * refresh token
     * @param refreshParam {@link RefreshParam}
     * @return  {@link RefreshVo}
     */
    RefreshVo refreshToken(@Valid RefreshParam refreshParam);
}
