package com.zero.rainy.core.model;


import java.util.*;

/**
 * Only Key-Value
 * @author Zero.
 * <p> Created on 2025/1/11 15:05 </p>
 */
public record Only<K, V>(K key, V value) implements Map<K, V> {

    private static final int ONE = 1;
    private static final String DEFAULT_KEY = "key";

    public static <K, V> Only<K, V> of(K key, V value) {
        return new Only<>(key, value);
    }
    public static <V> Only<String, V> of(V value) {
        return of(DEFAULT_KEY, value);
    }

    @Override
    public int size() {
        return ONE;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return Objects.equals(this.key, key);
    }

    @Override
    public boolean containsValue(Object value) {
        return Objects.equals(this.value, value);
    }

    @Override
    public V get(Object key) {
        return Objects.equals(this.key, key) ? value : null;
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException("Only is immutable");
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException("Only is immutable");
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException("Only is immutable");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Only is immutable");
    }

    @Override
    public Set<K> keySet() {
        return Collections.singleton(this.key);
    }

    @Override
    public Collection<V> values() {
        return Collections.singleton(this.value);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Collections.singleton(new AbstractMap.SimpleEntry<>(this.key, this.value));
    }
}
