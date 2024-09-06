package com.zero.rainy.grpc.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.google.protobuf.Message;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Protobuf 实体拷贝工具类
 *
 * @author Zero.
 * <p> Created on 2024/9/5 22:44 </p>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProtoUtils {

    /* 不需要拷贝的字段名 */
    private static final List<String> IGNORED_FIELD = List.of("serialVersionUID");
    private static final String GETTER_PREFIX = "get";
    private static final String SETTER_PREFIX = "set";
    private static final Map<Class<?>, Class<?>> TYPE_MAPPING = new HashMap<>();
    static {
        TYPE_MAPPING.put(String.class, String.class);
        TYPE_MAPPING.put(Integer.class, int.class);
        TYPE_MAPPING.put(Long.class, long.class);
        TYPE_MAPPING.put(Double.class, double.class);
        TYPE_MAPPING.put(Float.class, float.class);
        TYPE_MAPPING.put(Boolean.class, boolean.class);
        TYPE_MAPPING.put(Byte.class, byte.class);
        TYPE_MAPPING.put(Short.class, short.class);
        TYPE_MAPPING.put(int.class, int.class);
        TYPE_MAPPING.put(long.class, long.class);
        TYPE_MAPPING.put(double.class, double.class);
        TYPE_MAPPING.put(float.class, float.class);
        TYPE_MAPPING.put(boolean.class, boolean.class);
        TYPE_MAPPING.put(byte.class, byte.class);
        TYPE_MAPPING.put(short.class, short.class);
    }


    /**
     * Java entity copy to Protobuf entity
     *
     * @param source 源对象
     * @param clazz  目标对象类型
     */
    public static <T extends Message.Builder> T copy(Object source, Class<T> clazz){
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();
            copy(source, instance);
            return instance;
        } catch (Exception e) {
            log.error("java bean copy protobuf build fail", e);
            return null;
        }
    }

    /**
     * Java 实体属性 拷贝到 Protobuf 生成的实体中
     *
     * @param source    Java 实体
     * @param build     Protobuf 生成的实体构建器
     */
    public static <R,T extends Message.Builder> void copy(R source, T build){
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(build, "Target must not be null");

        // 获取源对象的所有字段(包括继承的父类字段)
        List<Field> fields = new ArrayList<>();
        Class<?> sourceClass = source.getClass();
        while (sourceClass != null && !sourceClass.equals(Object.class)){
            fields.addAll(Arrays.asList(sourceClass.getDeclaredFields()));
            sourceClass = sourceClass.getSuperclass();
        }
        Class<? extends Message.Builder> targetClass = build.getClass();
        // 遍历源对象所有字段
        for (Field field : fields) {
            String fieldName = field.getName();
            if (IGNORED_FIELD.contains(fieldName)){
                return;
            }
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(source);
                if (fieldValue == null){
                    return;
                }else if (fieldValue instanceof String value && StringUtils.isBlank(value)){
                    continue;
                }
                // field getter
                String getterMethodName = GETTER_PREFIX + toUpperCaseFirstOne(fieldName);
                Method targetGetterMethod;
                try {
                    targetGetterMethod = targetClass.getDeclaredMethod(getterMethodName);
                } catch (NoSuchMethodException e) {
                    // 字段在目标类中不存在,忽略
                    continue;
                }
                // 获取 target 的字段值，和值的类型
                Object targetFieldValue = targetGetterMethod.invoke(build);
                Class<?> targetFieldType = targetFieldValue.getClass();

                // 通过 setter 设置值
                String setterMethodName = SETTER_PREFIX + toUpperCaseFirstOne(fieldName);
                Method targetSetterMethod = targetClass.getDeclaredMethod(setterMethodName, getFieldType(targetFieldType));
                invokeSetter(fieldValue, targetFieldType, build, targetSetterMethod);
            } catch (Exception e) {
                log.error("java bean copy protobuf build fail", e);
            }
        }
    }

    /**
     * 执行 Setter 方法
     * @param value     字段值
     * @param fieldType 字段在目标类中的类型
     * @param target    操作对象
     * @param method    setter 方法
     */
    private static void invokeSetter(Object value, Class<?> fieldType, Object target, Method method) throws InvocationTargetException, IllegalAccessException {
        if (value instanceof List<?> || value instanceof Set<?> || value instanceof Map<?,?>){
            return;
        }
        // 如果值为时间类型，但目标对象中为字符串
        if (value instanceof LocalDate date){
            if (fieldType.equals(String.class)){
                method.invoke(target, LocalDateTimeUtil.format(date, DatePattern.NORM_DATE_PATTERN));
            }
            if (fieldType.equals(Timestamp.class)){
                long milli = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                method.invoke(target, Timestamps.fromMillis(milli));
            }
            return;
        }
        if (value instanceof LocalDateTime dateTime){
            if (fieldType.equals(String.class)){
                method.invoke(target, LocalDateTimeUtil.format(dateTime, DatePattern.NORM_DATETIME_PATTERN));
            }
            if (fieldType.equals(Timestamp.class)){
                long milli = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                method.invoke(target, Timestamps.fromMillis(milli));
            }
            return;
        }
        if (value instanceof BigDecimal val){
            if (fieldType.equals(double.class) || fieldType.equals(Double.class)){
                method.invoke(target, val.doubleValue());
            }
            if (fieldType.equals(float.class) || fieldType.equals(Float.class)){
                method.invoke(target, val.floatValue());
            }
            if (fieldType.equals(String.class)){
                method.invoke(target, val.toString());
            }
        }
        method.invoke(target, value);
    }

    private static Class<?> getFieldType(Class<?> fieldType) {
        return TYPE_MAPPING.get(fieldType);
    }

    private static String toUpperCaseFirstOne(String fieldName){
        if (fieldName == null || fieldName.isEmpty() || Character.isUpperCase(fieldName.charAt(0))){
            return fieldName;
        }
        return Character.toUpperCase(fieldName.charAt(0)) +
                fieldName.substring(1);
    }
}
