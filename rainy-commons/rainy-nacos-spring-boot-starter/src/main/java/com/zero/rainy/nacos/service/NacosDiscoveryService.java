package com.zero.rainy.nacos.service;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Nacos Discovery
 *
 * @author Zero.
 * <p> Created on 2025/1/5 18:10 </p>
 */
@Slf4j
@Service
public class NacosDiscoveryService {
    private final NacosServiceManager manager;
    private final NamingService namingService;
    private final NacosDiscoveryProperties properties;

    public NacosDiscoveryService(NacosServiceManager manager, NacosDiscoveryProperties properties) {
        this.manager = manager;
        this.namingService = this.manager.getNamingService();
        this.properties = properties;
    }


    /**
     * 获取指定服务的实例列表
     * @param serviceName   服务名
     * @param groupName     组名
     */
    public List<Instance> getAllInstances(String serviceName, String groupName) throws NacosException {
        return namingService.getAllInstances(serviceName, groupName);
    }


    /**
     * 注册服务实例
     *
     * @param serviceName 服务名
     * @param groupName   组名
     * @param ip          IP地址
     * @param port        端口号
     * @return            是否注册成功
     */
    public boolean registerInstance(String serviceName, String groupName, String ip, int port) {
        Instance instance = new Instance();
        instance.setIp(ip);
        instance.setPort(port);
        instance.setHealthy(true);
        try {
            namingService.registerInstance(serviceName, groupName, instance);
            return Boolean.TRUE;
        } catch (NacosException e) {
            log.error("failed to register service instance", e);
            return false;
        }
    }

    /**
     * 注销服务实例
     * @param serviceName   服务名
     * @param groupName     组名
     * @param ip            IP地址
     * @param port          端口号
     */
    public boolean deregisterInstance(String serviceName, String groupName, String ip, int port) {
        try {
            namingService.deregisterInstance(serviceName, groupName, ip, port);
            return Boolean.TRUE;
        } catch (NacosException e) {
            log.error("failed to deregister service instance", e);
            return false;
        }
    }

    @PostConstruct
    public void init() throws NacosException {
        // 注册一个虚假的服务
        if (this.registerInstance("go-sample-service", properties.getGroup(), "127.0.0.1", 12001)){
            log.info("register service success");
        }
        // 半分钟后手动注销实例
        new Thread(()-> {
            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (this.deregisterInstance("go-sample-service", properties.getGroup(), "127.0.0.1", 12001)){
                log.info("deregister service success");
            }
        }).start();
    }


    @PreDestroy
    public void destroy() throws NacosException {
//        namingService.deregisterInstance("test-service", "127.0.0.1", 18001);
    }
}
