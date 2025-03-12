package com.zero.rainy.core.enums.supers;

import java.util.EnumSet;
import java.util.Objects;

/**
 * 基础枚举
 *
 * @author Zero.
 * <p> Created on 2025/3/11 21:40 </p>
 */
public interface SuperEnum<T> {
    T getValue();
    String getLabel();

    /**
     * 根据 value值 获取目标枚举的项
     *
     * @param value  Value 值
     * @param clazz  目标枚举Class
     * @param <E>    目标枚举类型
     * @param <T>    Value类型
     * @return  {@link E}
     */
    static <E extends Enum<E> & SuperEnum<T>, T> E getByValue(T value, Class<E> clazz) {
        Objects.requireNonNull(value, "value must not be null");
        Objects.requireNonNull(clazz, "clazz must not be null");
        EnumSet<E> enums = EnumSet.allOf(clazz);
        for (E item : enums) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return null;
    }


    /**
     * 根据 label 名称 获取目标枚举项
     * @param label     标签名
     * @param clazz     枚举Class
     * @return {@link E}
     */
    static <E extends Enum<E> & SuperEnum<?>> E getByLabel(String label, Class<E> clazz) {
        Objects.requireNonNull(label, "label must not be null");
        Objects.requireNonNull(clazz, "clazz must not be null");
        for (E item : EnumSet.allOf(clazz)) {
            if (Objects.equals(item.getLabel(), label)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 根据 ordinal 获取枚举项
     * @param ordinal 枚举项索引
     */
    static <E extends Enum<E> & SuperEnum<?>> E getByOrdinal(Integer ordinal, Class<E> clazz) {
        Objects.requireNonNull(ordinal, "ordinal must not be null");
        Objects.requireNonNull(clazz, "clazz must not be null");
        E[] enums = clazz.getEnumConstants();
        if (ordinal < 0 || ordinal >= enums.length) {
            throw new IllegalArgumentException("ordinal " + ordinal + " is out of range");
        }
        return enums[ordinal];
    }

    /**
     * 根据 name 获取枚举项
     * @param name  枚举名称
     */
    static <E extends Enum<E> & SuperEnum<?>> E getByName(String name, Class<E> clazz) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(clazz, "clazz must not be null");
        for (E item : EnumSet.allOf(clazz)) {
            if (item.name().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}
