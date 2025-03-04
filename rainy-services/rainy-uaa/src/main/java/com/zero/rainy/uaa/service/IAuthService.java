package com.zero.rainy.uaa.service;

import com.zero.rainy.uaa.model.param.LoginParam;
import com.zero.rainy.uaa.model.vo.LoginVo;

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
}
