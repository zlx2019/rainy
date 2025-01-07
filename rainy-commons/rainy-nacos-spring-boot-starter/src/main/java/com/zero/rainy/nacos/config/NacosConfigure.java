package com.zero.rainy.nacos.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * Nacos 服务发现 & 服务配置
 *
 * @author Zero.
 * <p> Created on 2025/1/5 19:00 </p>
 */
@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(NacosDiscoveryProperties.class)
public class NacosConfigure {
    private final NacosDiscoveryProperties properties;


    /**
     * 注入 NamingService, 用于管理服务
     */
//    @Bean
    public NamingService namingService() {
        try {
            return NacosFactory.createNamingService(getProps());
        } catch (NacosException e) {
            throw new BeanCreationException("failed to create NameService bean", e);
        }
    }


    /**
     * 注入 ConfigService, 用于管理配置
     */
    @Bean
    public ConfigService configService() {
        try {
            return NacosFactory.createConfigService(getProps());
        } catch (NacosException e) {
            throw new BeanCreationException("failed to create ConfigService bean", e);
        }
    }


    private Properties getProps() {
        Properties props = new Properties();
        props.setProperty(PropertyKeyConst.SERVER_ADDR, properties.getServerAddr());
        props.setProperty(PropertyKeyConst.NAMESPACE, properties.getNamespace());
        props.setProperty(PropertyKeyConst.USERNAME, properties.getUsername());
        props.setProperty(PropertyKeyConst.PASSWORD, properties.getPassword());
        return props;
    }
}
