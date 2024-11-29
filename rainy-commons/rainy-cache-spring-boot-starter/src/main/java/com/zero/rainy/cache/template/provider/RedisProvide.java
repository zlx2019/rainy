package com.zero.rainy.cache.template.provider;

import com.zero.rainy.cache.template.CacheTemplate;
import com.zero.rainy.core.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * 缓存服务客户端
 *
 * @author Zero.
 * <p> Created on 2024/10/2 10:39 </p>
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("all")
public class RedisProvide implements CacheTemplate {
    private final RedisTemplate<String, Object> template;

    private ValueOperations<String, Object> opsValue() {
        return template.opsForValue();
    }
    private ListOperations<String, Object> opsList(){
        return template.opsForList();
    }
    private RedisSerializer<String> keySerializer() {
        return template.getStringSerializer();
    }
    private RedisSerializer valueSerializer() {
        return template.getValueSerializer();
    }

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void set(String key, Object value) {
        opsValue().set(key, value);
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    @Override
    public Object get(String key) {
        return opsValue().get(key);
    }

    /**
     * 获取缓存
     *
     * @param key   键
     * @param clazz 值的类型
     */
    @Override
    public <T> T get(String key, Class<T> clazz) {
        Object value = this.get(key);
        if (value == null)return null;
        return convertValue(value, clazz);
    }


    /**
     * 设置缓存
     *
     * @param expire 有效时长
     * @param unit   时长单位
     */
    @Override
    public void setEx(String key, Object value, long expire, TimeUnit unit) {
        opsValue().set(key, value, expire, unit);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean setNx(String key, Object value, Duration expire) {
        return template.execute((RedisCallback<Boolean>) conn -> {
            byte[] keys = keySerializer().serialize(key);
            byte[] values = valueSerializer().serialize(value);
            if (keys == null || values == null) {
                return false;
            }
            if (expire.isZero()){
                return conn.commands().setNX(keys, values);
            }
            return conn.commands().set(keys, values, Expiration.from(expire), RedisStringCommands.SetOption.ifAbsent());
        });
    }

    /**
     * 向List左侧插入多个元素
     *
     * @param key    列表Key
     * @param values 元素序列
     * @return 索引
     */
    @Override
    public <T> Long lPush(String key, T... values) {
        return opsList().leftPushAll(key, values);
    }

    /**
     * 从 List 右侧弹出一个元素
     *
     * @param key   key
     * @param clazz 元素原型
     */
    @Override
    public <T> T rPop(String key, Class<T> clazz) {
        Object value = opsList().rightPop(key);
        if (Objects.nonNull(value)){
            return convertValue(value, clazz);
        }
        return null;
    }

    @Override
    public Object rPop(String key) {
        return opsList().rightPop(key);
    }

    /**
     * 从 List 右侧阻塞式弹出一个元素。
     *
     * @param timeout 超时时间
     */
    @Override
    public <T> T brPop(String key, Duration timeout, Class<T> clazz) {
        Object value = opsList().rightPop(key, timeout);
        if (Objects.nonNull(value)){
            return convertValue(value, clazz);
        }
        return null;
    }

    /**
     * 从 List 右侧弹出多个元素
     */
    @Override
    public <T> List<T> rPop(String key, int count, Class<T> clazz) {
        List<Object> list = opsList().rightPop(key, count);
        if (CollectionUtils.isEmpty(list)){
            return List.of();
        }
        return list.stream().filter(Objects::nonNull)
                .map(value-> convertValue(value, clazz)).collect(Collectors.toList());
    }

    /**
     * 类型转换
     * @param value 要转换的值
     * @param clazz 要转换的类型
     */
    private <T> T convertValue(Object value, Class<T> clazz) {
        if (clazz.isInstance(value)){
            return clazz.cast(value);
        }
        // 数字类型转换.
        if (value instanceof Number number){
            if (clazz == Long.class || clazz == long.class){
                return clazz.cast(number.longValue());
            }
            if (clazz == Integer.class || clazz == int.class){
                return clazz.cast(number.intValue());
            }
            if (clazz == Short.class || clazz == short.class){
                return clazz.cast(number.shortValue());
            }
            if (clazz == Byte.class || clazz == byte.class){
                return clazz.cast(number.byteValue());
            }
            if (clazz == Double.class || clazz == double.class){
                return clazz.cast(number.doubleValue());
            }
            if (clazz == Float.class || clazz == float.class){
                return clazz.cast(number.floatValue());
            }
        }
        if (String.class == clazz){
            return clazz.cast(value.toString());
        }
        if (value instanceof String){
            return clazz.cast(JsonUtils.marshal(value));
        }
        throw new IllegalArgumentException("Cannot convert " + value + " to " + clazz.getName());
    }
}
