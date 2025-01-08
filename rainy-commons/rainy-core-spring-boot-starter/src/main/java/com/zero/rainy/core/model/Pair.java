package com.zero.rainy.core.model;


/**
 * @author Zero.
 * <p> Created on 2024/10/1 08:48 </p>
 *
 * @param key   属性名
 * @param value 值
 */
public record Pair<K, V>(K key, V value) {
    public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<>(key, value);
    }
}
