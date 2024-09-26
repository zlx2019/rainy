package com.zero.rainy.sample.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.zero.rainy.core.pojo.bo.MessageBo;

import java.lang.reflect.Type;

/**
 * @author Zero.
 * <p> Created on 2024/9/26 18:18 </p>
 */
public class Test {
    public static void main(String[] args) {
        TypeReference<MessageBo> type = new TypeReference<>() {
        };
        Class<? extends Type> aClass = type.getType().getClass();
        Type type1 = type.getType();
        String typeName = type1.getTypeName();
        Class<? extends Type> type1Class = type1.getClass();
        System.out.println(aClass.getTypeName());
    }

}
