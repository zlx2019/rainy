package com.zero.rainy.api.web;

import com.zero.rainy.core.constant.ServiceConstant;
import com.zero.rainy.core.pojo.dto.LoginRequestDTO;
import com.zero.rainy.core.pojo.dto.LoginResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

/**
 * User 服务 WebClient 异步客户端
 *
 * @author Zero.
 * <p> Created on 2024/9/13 14:06 </p>
 */
@HttpExchange(url = "http://" + ServiceConstant.USER, contentType = MediaType.APPLICATION_JSON_VALUE, accept = MediaType.APPLICATION_JSON_VALUE)
public interface UserServiceClient {


    /**
     * 用户登录(异步接口)
     * @param requestDTO  登录用户信息
     */
    @PostExchange("/user/login")
    Mono<LoginResponseDTO> login(@RequestBody LoginRequestDTO requestDTO);
}
