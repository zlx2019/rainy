package com.zero.rainy.gen.model.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 数据表 Vo
 *
 * @author Zero.
 * <p> Created on 2025/1/2 12:37 </p>
 */
@Data
public class TableVo {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String comment;

    /**
     * 表创建时间
     */
    private LocalDateTime createTime;
}
