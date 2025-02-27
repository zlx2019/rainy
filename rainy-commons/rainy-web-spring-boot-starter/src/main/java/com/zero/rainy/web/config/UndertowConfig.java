package com.zero.rainy.web.config;


import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xnio.Options;

/**
 * Undertow 容器配置
 *
 * @author Zero.
 * <p> Created on 2025/1/3 15:10 </p>
 */
@Configuration
public class UndertowConfig {

    @Bean
    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> webServerFactoryCustomizer(){
        return factory -> {
            factory.addBuilderCustomizers(builder -> {
                // 工作线程名称
                builder.setWorkerOption(Options.WORKER_NAME, "Work");
            });
        };
    }
}
