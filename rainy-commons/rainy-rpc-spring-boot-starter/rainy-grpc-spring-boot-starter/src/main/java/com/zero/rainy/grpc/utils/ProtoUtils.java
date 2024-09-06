package com.zero.rainy.grpc.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.google.protobuf.Message;
import com.google.protobuf.Timestamp;
import com.google.protobuf.util.Timestamps;
import com.zero.rainy.api.grpc.pb.user.UserServ;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

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
        String typeName = fieldType.getName();
        return switch (typeName) {
            case "java.lang.String" -> String.class;
            case "int", "java.lang.Integer" -> int.class;
            case "long", "java.lang.Long" -> long.class;
            case "byte", "java.lang.Byte" -> byte.class;
            case "double", "java.lang.Double" -> double.class;
            case "float", "java.lang.Float" -> float.class;
            case "short", "java.lang.Short" -> short.class;
            case "boolean", "java.lang.Boolean" -> boolean.class;
            default -> fieldType;
        };
    }

    /**
     * 将字段名改为首字母大写
     */
    private static String toUpperCaseFirstOne(String fieldName){
        if (fieldName == null || fieldName.isEmpty() || Character.isUpperCase(fieldName.charAt(0))){
            return fieldName;
        }
        return Character.toUpperCase(fieldName.charAt(0)) +
                fieldName.substring(1);
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        User user = new User();
        user.setUsername("12312");
        user.setPrice(213.24);
        user.setLocked(true);
        user.setScore(100.23f);
        user.setSendTime(LocalDateTime.now());
        user.setUid(213124512412L);
        UserServ.UserRequest.Builder builder = UserServ.UserRequest.newBuilder();
        copy(user, builder);
//        BeanUtils.copyProperties(user,builder);
        UserServ.UserRequest userRequest = builder.build();
//        System.out.println(userRequest.getUsername());
        log.info("{}", userRequest);
    }
}
