package com.zero.rainy.limit;

/**
 * 限流类型
 *
 * @author Zero.
 * <p> Created on 2024/11/26 21:42 </p>
 */
public enum LimitType {
    /**
     * 根据IP限流
     */
    IP,

    /**
     * 根据API参数限流(重复调用相同的API，处理相同的数据)
     */
    ARGS,

    /**
     * 根据API限流
     */
    API,

    /**
     * 根据用户Token限流
     */
    TOKEN,

    /**
     * 根据关键字限流，相同关键字则存在互斥关系.
     */
    KEY_WORD,
    ;
}
