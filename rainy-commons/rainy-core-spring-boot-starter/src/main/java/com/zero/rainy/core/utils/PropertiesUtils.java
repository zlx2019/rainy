package com.zero.rainy.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Properties;

/**
 * @author Zero.
 * <p> Created on 2024/12/10 15:48 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PropertiesUtils {


    public static Properties getIMAPConfig(String protocol, String host, int port){
        return getConfigs(protocol, host, port);
    }

    /**
     * 构建 邮件协议 Properties 配置
     * @param protocol  协议
     * @param host      主机
     * @param port      端口
     */
    private static Properties getConfigs(String protocol, String host, int port) {
        Properties props = new Properties(12);
        // 目标服务器信息
        props.put("mail." + protocol + ".host", host);
        props.put("mail." + protocol + ".port", port);
        // 连接服务器超时时间
        props.put("mail." + protocol + ".connectiontimeout", 30_000);
        // 读取邮件超时时间
        props.put("mail." + protocol + ".timeout", 30_000);

        // 开启认证 & ssl/tls
        props.put("mail." + protocol + ".auth", true);
        props.put("mail." + protocol + ".ssl.enable", true);
        // 认证方式
        // XOAUTH2 认证
         props.put("mail." + protocol + ".auth.mechanisms", "XOAUTH2");
        return props;
    }
}
