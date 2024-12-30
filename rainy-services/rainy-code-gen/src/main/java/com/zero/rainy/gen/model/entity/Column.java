package com.zero.rainy.gen.model.entity;

import lombok.Data;

/**
 * @author Zero.
 * <p> Created on 2024/12/30 17:54 </p>
 */
@Data
public class Column {
    /**
     * 表字段名
     */
    private String columnName;

    /**
     * 表字段类型，如 varchar、bit
     */
    private String columnType;

    /**
     * 表字段注释
     */
    private String comment;

    /**
     * 字段Key
     */
    private String primaryKey;

    /**
     * 字段的额外信息，如: auto_increment
     */
    private String extra;
}
