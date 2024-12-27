package com.zero.rainy.dashboard.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * RocketMQ admin config
 *
 * @author Zero.
 * <p> Created on 2024/12/27 15:45 </p>
 */
@Slf4j
@Configuration
public class RocketAdminConfig implements DisposableBean {
    @Value("${rocketmq.name-server}")
    public String nameServerAddr;

    private DefaultMQAdminExt admin;

    @Bean
    public DefaultMQAdminExt defaultMQAdminExt(){
        admin = new DefaultMQAdminExt();
        admin.setNamesrvAddr(nameServerAddr);
        try {
            admin.start();
        }catch (MQClientException e){
            throw new BeanCreationException("create bean: DefaultMQAdminExt fail");
        }
        return admin;
    }

    @Override
    public void destroy() throws Exception {
        if (Objects.nonNull(admin)){
            log.info("DefaultMQAdminExt shutdown");
            admin.shutdown();
        }
    }
}
