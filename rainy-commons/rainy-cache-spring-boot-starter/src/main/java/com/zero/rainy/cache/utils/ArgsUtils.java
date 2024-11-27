package com.zero.rainy.cache.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 参数处理工具
 *
 * @author Zero.
 * <p> Created on 2024/11/27 13:49 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArgsUtils {
    private static final String NULL = "null";


    /**
     * Any to String
     *  - Array -> [,,,]
     *  - Map | Entity -> {xx:xx}
     */
    public static String toString(Object obj){
        if (obj == null){
            return NULL;
        }else if (obj instanceof String value){
            return value;
        } else if (obj instanceof Number || obj instanceof Boolean){
            return obj.toString();
        } else if (obj.getClass().isArray()){
            return null;
        }
        return null;
    }
}
