package com.zero.rainy.auth.controller;

import com.zero.rainy.core.model.Result;
import com.zero.rainy.security.model.OTT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ott.GenerateOneTimeTokenRequest;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import org.springframework.security.authentication.ott.OneTimeTokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 一次性令牌
 *
 * @author Zero.
 * <p> Created on 2025/3/7 15:21 </p>
 */
@RestController
@RequestMapping("/ott")
@RequiredArgsConstructor
public class OTTController {
    private final OneTimeTokenService oneTimeTokenService;

    /**
     * OTT Token grant
     * @param username token username
     * @return  {@link OneTimeToken} token
     */
    @PostMapping("/manual/grant")
    public Result<OTT> grant(String username) {
        OneTimeToken token = oneTimeTokenService.generate(new GenerateOneTimeTokenRequest(username));
        return Result.ok(new OTT(token));
    }

    /**
     * Consume OTT token
     */
    @PostMapping("/manual/consume")
    public Result<Boolean> sent(String username, String token) {
        if (Objects.isNull(oneTimeTokenService.consume(new OneTimeTokenAuthenticationToken(username, token)))){
            // 令牌已被消费过
            return Result.ok(Boolean.FALSE);
        }
        return Result.ok(Boolean.TRUE);
    }

    @GetMapping("/consume/success")
    public Result<String> sentSuccess() {
        return Result.ok("success");
    }
}
