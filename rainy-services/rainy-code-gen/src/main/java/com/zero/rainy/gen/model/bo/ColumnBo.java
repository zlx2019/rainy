package com.zero.rainy.gen.model.bo;

import com.zero.rainy.core.utils.CloneUtils;
import com.zero.rainy.gen.model.entity.Column;
import lombok.Data;

/**
 * 数据表列和实体属性
 *
 * @author Zero.
 * <p> Created on 2024/12/30 15:11 </p>
 */
@Data
public class ColumnBo {
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

    /**
     * 实体属性名 (帕斯卡命名) user_name -> UserName
     */
    private String attrNamePascalCase;

    /**
     * 实体属性名 (驼峰命名) user_name -> userName
     */
    private String attrNameCamelCase;

    /**
     * 实体属性类型, 如: String, Boolean
     */
    private String attrType;

    public ColumnBo(Column column) {
        CloneUtils.copyProperties(column, this);
    }
}
