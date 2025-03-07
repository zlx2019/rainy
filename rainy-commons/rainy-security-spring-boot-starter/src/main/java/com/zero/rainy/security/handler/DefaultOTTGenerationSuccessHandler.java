package com.zero.rainy.security.handler;

import com.zero.rainy.security.model.OTT;
import com.zero.rainy.web.utils.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.web.authentication.ott.OneTimeTokenGenerationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OOT 令牌自动生成端点, 请求必须携带 `username` 参数.
 *
 * @author Zero.
 * <p> Created on 2025/3/7 15:16 </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultOTTGenerationSuccessHandler implements OneTimeTokenGenerationSuccessHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, OneTimeToken oneTimeToken) throws IOException, ServletException {
        log.info("[OTT-token] Auto gen: {} - {}", oneTimeToken.getUsername(), oneTimeToken.getTokenValue());
        ResponseUtils.responseOk(response, new OTT(oneTimeToken));
    }
}
