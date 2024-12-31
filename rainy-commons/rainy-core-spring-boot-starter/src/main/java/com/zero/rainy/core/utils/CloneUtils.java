package com.zero.rainy.core.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (Objects.isNull(source)) {
            return null;
        }
        try {
            T target = tClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            log.error("Bean clone error:{}", e.getMessage());
//             throw new CloneRuntimeException(e);
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

    /**
     * @param selectedProperties 选择需要 copy 的属性
     * @return new {@link ArrayList}
     */
    public static <S,T> List<T> copyProperties(Collection<S> sources, Class<T> tClass, Set<String> selectedProperties)  {
        if(CollectionUtils.isEmpty(sources)) {
            return List.of();
        }

        Optional<S> firstOptional = sources.stream().findFirst();
        Class<?> sourceClazz = firstOptional.get().getClass();
        List<T> targets = new ArrayList<>(sources.size());

        Map<String, Field> sourceFieldMappings = getDeclaredFieldsMappings(sourceClazz);
        Map<String,Field> targetFieldMappings = getDeclaredFieldsMappings(tClass);

        try {
            Constructor<T> declaredConstructor = tClass.getDeclaredConstructor();
            for (S source : sources) {
                T target = declaredConstructor.newInstance();
                copyProperties(source,target,sourceFieldMappings,targetFieldMappings,selectedProperties);
                targets.add(target);
            }
        } catch(ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        return targets;
    }

    /**
     * 可选复制属性的对象
     */
    public static <S,T> void copyProperties(S source,T target,Set<String> selectedProperties) {

        Objects.requireNonNull(source);
        Objects.requireNonNull(target);

        Class<?> sClass = source.getClass();
        Class<?> tClass = target.getClass();

        Map<String, Field> sourceFieldsMapping = getDeclaredFieldsMappings(sClass);
        Map<String, Field> targetFieldsMapping = getDeclaredFieldsMappings(tClass);
        copyProperties(source,target,sourceFieldsMapping,targetFieldsMapping,selectedProperties);

    }


    /**
     * 选择性拷贝属性，核心代码，方便复用
     * {@link this#copyProperties(Object, Class, Set)}
     * {@link this#copyProperties(Collection, Class, Set)}
     * @param sourceFieldsMapping fields of source
     * @param targetFieldsMapping fields of target
     * @param selectedProperties selected field
     */
    protected static <S, T> void copyProperties(
            S source, T target, Map<String, Field> sourceFieldsMapping,
            Map<String, Field> targetFieldsMapping, Set<String> selectedProperties) {

        try {
            for (Map.Entry<String, Field> entry : sourceFieldsMapping.entrySet()) {
                String fieldName = entry.getKey();
                Field sourceField = entry.getValue();
                Field targetField = targetFieldsMapping.get(fieldName);
                if (selectedProperties.contains(fieldName) && Objects.nonNull(targetField)) {
                    Class<?> sourceFieldType = sourceField.getType();
                    Class<?> targetFieldType = targetField.getType();
                    if (targetFieldType.isAssignableFrom(sourceFieldType)) {
                        ReflectionUtils.makeAccessible(sourceField);
                        ReflectionUtils.makeAccessible(targetField);
                        targetField.set(target, sourceField.get(source));
                    }
                }
            }

        } catch (IllegalAccessException e) {
            log.error(e.getMessage(),e);
        }
    }

    public static <S, T> T copyProperties(S source, Class<T> tClass, Set<String> selectedProperties) {
        try {
            T instance = tClass.getDeclaredConstructor().newInstance();
            copyProperties(source,instance,selectedProperties);
        } catch (ReflectiveOperationException e) {
            log.error(e.getMessage(),e);
        }
        return null;
    }

    private static Map<String,Field> getDeclaredFieldsMappings(Class<?> clazz) {
        return Stream.of(clazz.getDeclaredFields()).collect(Collectors.toMap(Field::getName, Function.identity()));
    }
}
