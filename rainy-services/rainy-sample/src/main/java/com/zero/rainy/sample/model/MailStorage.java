package com.zero.rainy.sample.model;

import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Zero.
 * <p> Created on 2024/12/5 10:44 </p>
 */
@Data
public class MailStorage<V extends Map<String, ?>> {
    /**
     * 邮箱账号
     */
    private String key;

    /**
     * 存储元素
     */
    private V elems;
}
