package com.zero.rainy.cache.utils;

import com.zero.rainy.cache.enums.RedisKey;
import com.zero.rainy.core.constant.Constant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * Redis Key 构建工具
 *  格式: [服务]:[模块]:[业务]:{自定义业务后缀}...
 *  示例: [user]:[auth]:[login-token]:userId
 *
 * @author Zero.
 * <p> Created on 2024/11/27 17:51 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RedisHelper {
    public static final String KEY_DELIMITER = ":";
    private static final String KEY_WILDCARD = "*";

    /**
     * 根据{@link RedisKey} 构建Key
     *
     * @param prefix 前缀
     * @param args   后缀
     */
    public static String keyBuild(RedisKey prefix, String... args){
        return keyBuild(prefix.getWorkspace(), prefix.getService(), prefix.getModule(), prefix.getBusiness(), args);
    }

    /**
     *
     * @param workspace 所属项目
     * @param service   所属服务
     * @param module    所属模块
     * @param business  业务标识
     * @param args      额外参数
     * @return  [workspace]:[service]:[module]:[business]:[...args]
     */
    public static String keyBuild(String workspace, String service, String module, String business, String... args){
        if (StringUtils.isBlank(workspace)){
            workspace = Constant.PROJECT_NAME;
        }
        StringBuilder key = new StringBuilder(workspace);
        if (StringUtils.isNotBlank(service)){
            key.append(KEY_DELIMITER).append(service);
        }
        if (StringUtils.isNotBlank(module)){
            key.append(KEY_DELIMITER).append(module);
        }
        if (StringUtils.isNotBlank(business)){
            key.append(KEY_DELIMITER).append(business);
        }
        for (String arg : args) {
            if (StringUtils.isNotBlank(arg)){
                key.append(KEY_DELIMITER).append(arg);
            }
        }
        return key.toString();
    };
}
