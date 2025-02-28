package com.zero.rainy.cache.enums;


import com.zero.rainy.core.constant.Constant;

import java.time.Duration;

/**
 * Redis Key 前缀信息
 *
 * @author Zero.
 * <p> Created on 2025/2/27 23:03 </p>
 */
public interface RedisKey {

    /**
     * 所属项目
     */
    default String getWorkspace(){
        return Constant.PROJECT_NAME;
    };

    /**
     * 所属服务
     */
    String getService();

    /**
     * 所属模块
     */
    String getModule();

    /**
     * 所属业务
     */
    String getBusiness();

    /**
     * 缓存有效期
     */
    default Duration getExpire(){
        return Duration.ZERO;
    }
}
