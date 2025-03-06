package com.zero.rainy.uaa.controller;

import com.zero.rainy.core.model.Result;
import com.zero.rainy.uaa.model.param.LoginParam;
import com.zero.rainy.uaa.model.param.RefreshParam;
import com.zero.rainy.uaa.model.vo.LoginVo;
import com.zero.rainy.uaa.model.vo.RefreshVo;
import com.zero.rainy.uaa.service.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 退出登录
     * @return {@link Result<Void>}
     */
    @DeleteMapping("/logout")
    public Result<Void> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken) {
        return Result.ok();
    }

    /**
     * 根据刷新令牌，获取新的访问令牌
     * @param refreshParam 刷新令牌 {@link RefreshParam}
     * @return           访问令牌 {@link RefreshVo}
     */
    @PostMapping("/refresh")
    public Result<RefreshVo> refresh(@RequestBody @Valid RefreshParam refreshParam) {
        return Result.ok(authService.refreshToken(refreshParam));
    }
}
