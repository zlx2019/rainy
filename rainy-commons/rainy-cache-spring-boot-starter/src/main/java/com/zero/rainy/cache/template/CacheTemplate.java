package com.zero.rainy.cache.template;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 缓存服务模板接口
 *
 * @author Zero.
 * <p> Created on 2024/10/2 11:00 </p>
 */
@SuppressWarnings("all")
public interface CacheTemplate {

    /**
     * 设置缓存
     * @param key   键
     * @param value 值
     */
    void set(final String key, final Object value);

    /**
     * 获取缓存
     * @param key   键
     * @return      值
     */
    Object get(final String key);

    /**
     * 获取缓存
     * @param key   键
     * @param clazz 值的类型
     */
    <T> T get(final String key, final Class<T> clazz);

    /**
     * 设置带有效期的缓存
     * @param expire 有效期
     */
    default void setEx(final String key, final Object value, Duration expire){
        this.setEx(key, value, expire.toMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 设置带有效期的缓存
     * @param expire 有效时长
     * @param unit   时长单位
     */
    void setEx(final String key, final Object value, long expire, TimeUnit unit);

    /**
     * 原子性设置缓存
     * @return 设置是否成功, 已存在则设置失败
     */
    default Boolean setNx(final String key, final Object value){
        return this.setNx(key, value, Duration.ZERO);
    }

    /**
     * 原子性设置缓存
     * @param expire 有效期
     * @return 设置是否成功, 已存在则设置失败
     */
    Boolean setNx(final String key, final Object value, Duration expire);

    /**
     * 向List左侧插入多个元素
     * @param key    列表Key
     * @param values 元素序列
     * @return       索引
     */
    <T> Long lPush(final String key, final T... values);

    /**
     * 从 List 右侧弹出一个元素
     *
     * @param key   key
     * @param clazz 元素原型
     */
    <T> T rPop(final String key, final Class<T> clazz);

    Object rPop(final String key);

    /**
     * 从 List 右侧阻塞式弹出一个元素。
     * @param timeout 超时时间
     */
    <T> T brPop(final String key, Duration timeout, final Class<T> clazz);

    /**
     * 从 List 右侧弹出多个元素
     */
    <T> List<T> rPop(final String key, int count, final Class<T> clazz);
}
