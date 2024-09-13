package com.zero.rainy.webclient.factory;

import com.zero.rainy.api.web.UserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * WebClient 客户端代理工厂
 *
 * @author Zero.
 * <p> Created on 2024/9/13 14:33 </p>
 */
@Slf4j
@RequiredArgsConstructor
public class ClientProxyFactory {
    private final WebClient webClient;

    /**
     * 注入 {@link UserServiceClient} 代理客户端
     */
    @Bean
    public UserServiceClient userServiceClient() {
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build().createClient(UserServiceClient.class);
    }
}
