package com.zero.rainy.message.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.spring.autoconfigure.RocketMQProperties;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Rocket Admin Config
 *
 * @author Zero.
 * <p> Created on 2025/1/7 16:55 </p>
 */
@Slf4j
@RequiredArgsConstructor
public class RocketMQAdminConfig implements DisposableBean {
    private final RocketMQProperties properties;
    private DefaultMQAdminExt admin;

    @Bean
    public DefaultMQAdminExt defaultMQAdminExt() {
        admin = new DefaultMQAdminExt();
        admin.setNamesrvAddr(properties.getNameServer());
        try {
            admin.start();
        } catch (MQClientException e) {
            throw new BeanCreationException("create DefaultMQAdminExt bean failed", e);
        }
        return admin;
    }

    @Override
    public void destroy() throws Exception {
        if (admin != null) {
            admin.shutdown();
        }
    }
}
