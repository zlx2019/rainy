package com.zero.rainy.core.utils;

import cn.hutool.core.util.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 断言工具类
 *
 * @author Zero.
 * <p> Created on 2024/8/28 14:32 </p>
 */
@SuppressWarnings("all")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AssertUtils {

    private static final String FAIL = "assert failed.";

    /**
     * 断言: 当表达式结果为 false 则抛出异常
     * @param expr      表达式
     * @param message   异常消息
     */
    private static void asserts(boolean expr, String message){
        if (!expr){
            throwException(new IllegalStateException(message));
        }
    }
    private static void asserts(boolean expr){
        asserts(expr, FAIL);
    }

    /**
     * 断言: 字符串为 null 或 "" 时抛出异常
     *
     * @param str     字符串
     * @param message 异常消息
     */
    public static void isBlank(String str, String... message) {
        if (StringUtils.isBlank(str)) {
            throwIllegalArgumentException(message);
        }
    }

    /**
     * 其中任意一个字符串都不能为空
     * @param strings   字符串序列
     */
    public static void isBlanks(String... strings){
        List.of(strings).forEach(str-> isBlank(str, "any string must not be blank."));
    }

    /**
     * 当 表达式为真则抛出异常
     * @param expr      表达式
     * @param message   异常消息
     */
    public static void isTrue(boolean expr, String... message) {
        if (expr){
            throwIllegalArgumentException(message);
        }
    }

    public static void isTrue(boolean expr, RuntimeException e){
        if (expr){
            throw e;
        }
    }

    /**
     * 断言: 两个字符串不一致 抛出异常
     * @param str1    文本1
     * @param str2    文本2
     * @param message 异常消息
     */
    public static void notEquals(String str1, String str2, String message) {
        if (!StrUtil.equals(str1, str2)) {
            throwException(new IllegalArgumentException(message));
        }
    }

    /**
     * 断言: 对象不为空
     * @param obj       断言对象
     * @param message   异常消息
     */
    public static void nonNull(Object obj, String... message) {
        if (ObjectUtils.isEmpty(obj)) {
            throwIllegalArgumentException(message);
        }
    }

    /**
     * 断言: 多个对象都不能为空
     */
    public static void nonNulls(Object... objs){
        List.of(objs).forEach(obj-> nonNull(obj, "any object must not be null."));
    }

    /**
     * 断言: 对象为空
     *
     * @param obj     目标对象
     * @param message 异常消息
     */
    public static void isNull(Object obj, String... message) {
        if (ObjectUtils.isNotEmpty(obj)){
            throwIllegalArgumentException(message);
        }
    }

    /**
     * 断言: 多个对象都为空
     *
     * @param objects 对象列表
     */
    public static void isNulls(Object... objects) {
        List.of(objects).forEach(AssertUtils::isNull);
    }



    /**
     * 断言: 表达式结果为 false
     *
     * @param obj     目标参数
     * @param message 异常消息
     */
    public static void isFalse(boolean obj, String... message) {
        if (obj) throwIllegalArgumentException(message);
    }

    /**
     * 断言: 所有表达式结果都为 false
     */
    public static void isFalse(Boolean... expr) {
        List.of(expr).forEach(obj -> isFalse(obj, "any object must not be false."));
    }


    /**
     * 抛出非法参数异常
     */
    private static void throwIllegalArgumentException(String... messages){
        String message = FAIL;
        if (messages != null && messages.length > 0){
            message = messages[0];
        }
        throw new IllegalArgumentException(message);
    }

    /**
     * 统一抛出异常
     */
    private static void throwException(RuntimeException e){
        throw e;
    }
}