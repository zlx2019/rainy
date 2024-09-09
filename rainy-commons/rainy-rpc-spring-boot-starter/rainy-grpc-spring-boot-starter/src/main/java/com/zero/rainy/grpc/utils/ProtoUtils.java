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

    public static <B extends Message.Builder, T> T toEntity(B src, T dst) {
        Assert.notNull(src, "Source must not be null");
        Assert.notNull(dst, "Target must not be null");
        List<Field> fieldsAll = new ArrayList<>();
        Class<?> sourceClass = src.getClass();
        while (sourceClass != null && !sourceClass.equals(Object.class)){
            fieldsAll.addAll(Arrays.asList(sourceClass.getDeclaredFields()));
            sourceClass = sourceClass.getSuperclass();
        }
        System.out.println(fieldsAll.size());
        return null;
    }

    /**
     * Java entity copy to Protobuf entity
     *
     * @param src 源对象
     * @param clazz  目标对象类型
     */
    public static <B extends Message.Builder> B toProto(Object src, Class<B> clazz){
        try {
            Constructor<B> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            B instance = constructor.newInstance();
            toProto(src, instance);
            return instance;
        } catch (Exception e) {
            log.error("java bean copy protobuf build fail", e);
            return null;
        }
    }

    /**
     * Java 实体属性 拷贝到 Protobuf 生成的实体中
     *
     * @param src    Java 实体
     * @param dst     Protobuf 生成的实体构建器
     */
    public static <B extends Message.Builder> void toProto(Object src, B dst){
        Assert.notNull(src, "Source must not be null");
        Assert.notNull(dst, "Target must not be null");

        // 递归取出源对象的所有字段，包括其父类可访问的字段
        List<Field> fieldsAll = new ArrayList<>();
        Class<?> sourceClass = src.getClass();
        while (sourceClass != null && !sourceClass.equals(Object.class)){
            fieldsAll.addAll(Arrays.asList(sourceClass.getDeclaredFields()));
            sourceClass = sourceClass.getSuperclass();
        }

        // 遍历所有字段，根据字段名进行拷贝
        for (Field field : fieldsAll) {
            String fieldName = field.getName();
            // 过滤
            if (IGNORED_FIELD.contains(fieldName)){
                continue;
            }
            // 设置可访问
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(src);
                if (fieldValue == null || (fieldValue instanceof String value && StringUtils.isBlank(value))){
                    continue;
                }
                Class<? extends Message.Builder> targetClass = dst.getClass();
                // 通过 getter,setter 方法，将字段值设置到目标对象
                String getterMethodName = GETTER_PREFIX + toUpperCaseFirstOne(fieldName);
                Method getterMethod = targetClass.getDeclaredMethod(getterMethodName);
                Object dstFieldValue = getterMethod.invoke(dst);
                Class<?> dstFieldType = dstFieldValue.getClass();
                // 设置字段值
                String setterMethodName = SETTER_PREFIX + toUpperCaseFirstOne(fieldName);
                Method targetSetterMethod = targetClass.getDeclaredMethod(setterMethodName, getFieldType(dstFieldType));
                invokeSetter(fieldValue, dstFieldType, dst, targetSetterMethod);
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
        Object fieldValue = value;
        // 如果值为时间类型，但目标对象中为字符串
        if (value instanceof LocalDate date){
            if (fieldType.equals(String.class)){
                fieldValue = LocalDateTimeUtil.format(date, DatePattern.NORM_DATE_PATTERN);
            }
            if (fieldType.equals(Timestamp.class)){
                fieldValue = Timestamps.fromMillis(date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
        if (value instanceof LocalDateTime dateTime){
            if (fieldType.equals(String.class)){
                fieldValue = LocalDateTimeUtil.format(dateTime, DatePattern.NORM_DATETIME_PATTERN);
            }
            if (fieldType.equals(Timestamp.class)){
                fieldValue = Timestamps.fromMillis(dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
        }
        if (value instanceof BigDecimal val){
            if (fieldType.equals(double.class) || fieldType.equals(Double.class)){
                fieldValue = val.doubleValue();
            }
            if (fieldType.equals(float.class) || fieldType.equals(Float.class)){
                fieldValue = val.floatValue();
            }
            if (fieldType.equals(String.class)){
                fieldValue = val.toString();
            }
        }
        method.invoke(target, fieldValue);
    }

    /**
     * 将包装类型，转换为基本类型
     * @param fieldType 字段类型
     */
    private static Class<?> getFieldType(Class<?> fieldType) {
        return TYPE_MAPPING.get(fieldType);
    }

    /**
     * 将 属性名的首字母转成大写.
     * @param fieldName 原属性名
     */
    private static String toUpperCaseFirstOne(String fieldName){
        if (fieldName == null || fieldName.isEmpty() || Character.isUpperCase(fieldName.charAt(0))){
            return fieldName;
        }
        return Character.toUpperCase(fieldName.charAt(0)) +
                fieldName.substring(1);
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("12312");
        user.setPrice(213.24);
        user.setLocked(true);
        user.setScore(100.23f);
        user.setSendTime(LocalDateTime.now());
        user.setUid(213124512412L);
        UserServ.UserRequest.Builder builder = toProto(user, UserServ.UserRequest.Builder.class);
        log.info("{}", builder.build());

        User user1 = new User();
        toEntity(builder, user1);
        UserServ.UserRequest request = builder.build();
//        BeanUtils.copyProperties(builder, user1);
//        log.info("{}", user1);
    }
}
