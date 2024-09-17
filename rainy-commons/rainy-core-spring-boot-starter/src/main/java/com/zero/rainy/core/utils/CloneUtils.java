package com.zero.rainy.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 对象属性拷贝工具
 *
 * @author Zero.
 * <p> Created on 2024/9/16 02:45 </p>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CloneUtils {

    /**
     * 实体属性拷贝
     *
     * @param source 源对象
     * @param tClass 目标对象原型
     * @return 目标对象实例
     */
    public static <S, T> T copyProperties(S source, Class<T> tClass) {
        try {
            T target = tClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            log.error("Bean properties copy error:{}", e.getMessage());
            return null;
        }
    }
    public static <S,T> void copyProperties(S source, T target) {
        BeanUtils.copyProperties(source,target);
    }

    /**
     * copy 属性，扩展。
     * @param biConsumer S,T
     */
    public static <S,T> void copyProperties(S source, T target, BiConsumer<S,T> biConsumer) {
        BeanUtils.copyProperties(source,target);
        biConsumer.accept(source,target);
    }

    /**
     * 实体序列拷贝
     *
     * @param sources 源对象序列
     * @param tClass  目标对象原型
     * @return 目标对象序列
     */
    public static <S, T> List<T> copyProperties(Collection<S> sources, Class<T> tClass) {
        List<T> targets = new ArrayList<>(sources.size());
        try {
            for (S source : sources) {
                T instance = tClass.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(source, instance);
                targets.add(instance);
            }
            return targets;
        } catch (Exception e) {
            log.error("Bean list clone error:{}", e.getMessage());
            return List.of();
        }
    }
}
