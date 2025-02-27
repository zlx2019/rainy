package com.zero.rainy.cache.template;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.core.DefaultTypedTuple;

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

    /**
     * 向 Zset 中添加元素
     *
     * @param key   key
     * @param value 元素值
     * @param score 分值
     */
    <T> boolean zAdd(final String key, final T value, final Double score);


    /**
     * 向 Zset 中添加批量元素
     *
     * @param key key
     * @param tuples 元素列表
     */
    Long zAdd(final String key, final List<DefaultTypedTuple<Object>> tuples);

    /**
     * 根据分值范围删除元素
     *
     * @param key   key
     * @param begin 起始分值
     * @param end   结束分值
     */
    Long zRemByScore(final String key, double begin, double end);

    /**
     * 删除小于指定数值的所有元素
     * @param key   key
     * @param end   阈值
     */
    default Long zRemByScore(final String key, double end){
        return this.zRemByScore(key, Double.NEGATIVE_INFINITY, end);
    }

    /**
     * 获取 ZSet 中的元素数量
     * @param key
     */
    Long zCard(final String key);

    /**
     * 根据分值区间，统计元素数量
     */
    Long zCount(final String key, double begin, double end);

    /**
     * 统计出大于该分值的元素数量
     *
     * @param score 要大于的分值
     */
    default Long zCount(final String key, double score){
        return zCount(key, score, Double.POSITIVE_INFINITY);
    }

    /**
     * 从 ZSet 中根据分值从大到小弹出一批元素.
     *
     * @param key   key
     * @param count 弹出的元素数量
     */
    <T> List<T> zPopMax(final String key, final Integer count, final Class<T> clazz);

    default  <T> T zPopMax(final String key, final Class<T> clazz){
        List<T> list = this.zPopMax(key, 1, clazz);
        return CollectionUtils.isEmpty(list) ? null : list.getFirst();
    }

    /**
     * 弹出大于 score 分值的最大元素.
     *
     * @param key   Key
     * @param score 不能小于该分值
     */
    Object zPopMaxByScore(final String key, double score);

    /**
     * 从 ZSet 中根据分值从小到大弹出一批元素.
     * @param key   key
     * @param count 弹出的元素数量
     */
    <T> List<T> zPopMin(final String key, final Integer count, final Class<T> clazz);
    default  <T> T zPopMin(final String key, final Class<T> clazz){
        List<T> list = this.zPopMin(key, 1, clazz);
        return CollectionUtils.isEmpty(list) ? null : list.getFirst();
    }
}
