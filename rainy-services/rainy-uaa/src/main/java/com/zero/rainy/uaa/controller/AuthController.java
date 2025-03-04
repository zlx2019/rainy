package com.zero.rainy.uaa.controller;

import com.zero.rainy.core.model.Result;
import com.zero.rainy.uaa.model.param.LoginParam;
import com.zero.rainy.uaa.model.vo.LoginVo;
import com.zero.rainy.uaa.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 认证
 *
 * @author Zero.
 * <p> Created on 2025/3/4 13:39 </p>
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;


    /**
     * 用户登录
     * @param loginParam 登录参数
     * @return access token
     */
    @PostMapping("/login")
    public Result<LoginVo> login(@RequestBody @Valid LoginParam loginParam) {
        return Result.ok(authService.login(loginParam));
    }
}
