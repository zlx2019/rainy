package com.zero.rainy.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zero.
 * <p> Created on 2024/10/2 12:17 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeUtils {
    private static final List<Class<?>> INTEGER = List.of(
            Byte.class, Short.class, Integer.class, Long.class,
            Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE);

    public static boolean isInteger(Class<?> clazz) {
        return INTEGER.stream().anyMatch(type-> type.isAssignableFrom(clazz));
    }

    public static void main(String[] args) {
        Class<?> integerClass = int.class;
        System.out.println(isInteger(integerClass));
    }

    public static <T> boolean isLong(Class<T> clazz) {
        return clazz == Long.class || clazz == long.class;
    }
}
