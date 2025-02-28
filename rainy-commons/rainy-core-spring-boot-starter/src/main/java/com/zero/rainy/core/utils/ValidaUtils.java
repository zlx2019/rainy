package com.zero.rainy.core.utils;

import com.zero.rainy.core.ext.spring.SpringContextUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;


/**
 * 参数校验工具
 *
 * @author Zero.
 * <p> Created on 2024/9/20 13:43 </p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidaUtils {
    private static final Validator VALIDA = SpringContextUtil.getBean(Validator.class);

    public static <T> void validate(T source, Class<?>... groups){
        Set<ConstraintViolation<T>> set = VALIDA.validate(source, groups);
        if (!set.isEmpty()) {
            throw new ConstraintViolationException("参数校验异常", set);
        }
    }
}
