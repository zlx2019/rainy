package com.zero.rainy.webclient.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * WebClient 服务通信实例配置
 *
 * @author Zero.
 * <p> Created on 2024/9/4 14:29 </p>
 */
@RequiredArgsConstructor
public class WebClientConfigure {
    private final ReactorLoadBalancerExchangeFilterFunction loadBalancerRule;

    /**
     * 使用 Netty HttpClient 作为HTTP客户端
     */
    @Bean
    public HttpClient webClientHttp(){
        // HTTP Client 连接池配置
        ConnectionProvider provider = ConnectionProvider.builder("web-service-http-client")
                // 最大连接数,默认为CPU * 2
                .maxConnections(48)
                // 阻塞处理队列数量
                .pendingAcquireMaxCount(96)
                // 获取请求超时时间
                .pendingAcquireTimeout(Duration.ofSeconds(10))
                .build();
        // 定义一个loop来进行http的线程调度管理
        LoopResources loop = LoopResources.create("web-service-loop");
        return HttpClient.create(provider)
                // 设置连接超时时间
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .runOn(loop)
                .doOnConnected(conn -> {
                    // 设置连接读取和写入超时时间
                    conn.addHandlerFirst(new ReadTimeoutHandler(15, TimeUnit.SECONDS));
                    conn.addHandlerFirst(new WriteTimeoutHandler(15, TimeUnit.SECONDS));
                });
    }

    /**
     * 注入 WebClient 实例
     * @param webClientHttp 底层使用的HTTP客户端
     */
    @Bean
    public WebClient webClient(HttpClient webClientHttp){
        return WebClient.builder()
                .defaultHeaders(headers-> {
                    // 设置默认请求头
                })
                // 编解码器
                .codecs(codec-> codec.defaultCodecs().maxInMemorySize(-1))
                // 设置 HTTP 客户端
                .clientConnector(new ReactorClientHttpConnector(webClientHttp))
                // 设置全局拦截器
                .filters(filters-> {
                    filters.add(loadBalancerRule);
                })
                .build();
    }
}
