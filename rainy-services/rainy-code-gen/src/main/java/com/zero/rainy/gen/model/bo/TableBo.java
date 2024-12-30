package com.zero.rainy.gen.model.bo;

import com.zero.rainy.gen.model.entity.Table;
import lombok.Data;

import java.util.List;

/**
 * 数据表信息
 *
 * @author Zero.
 * <p> Created on 2024/12/30 15:11 </p>
 */
@Data
public class TableBo {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 该表的主键字段
     */
    private ColumnBo primaryKey;

    /**
     * 表的字段列表(不含主键)
     */
    private List<ColumnBo> columnBos;

    /**
     * 实体类名 (帕斯卡命名) sys_user -> SysUser
     */
    private String classNamePascalCase;

    /**
     * 实体类名 (驼峰命名) sys_user -> sysUser
     */
    private String classNameCamelCase;

    public TableBo(){}
    public TableBo(Table tableInfo) {
        this.comment = tableInfo.getComment();
        this.tableName = tableInfo.getTableName();
    }
}
