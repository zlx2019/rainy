package com.zero.rainy.gen.model.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 数据表基础信息
 *
 * @author Zero.
 * <p> Created on 2024/12/30 15:33 </p>
 */
@Data
public class Table {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 数据表存储引擎
     */
    private String engine;

    /**
     * 表创建时间
     */
    private LocalDateTime createTime;

    /**
     * 数据列
     */
    private List<Column> columns;
}
