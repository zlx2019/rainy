package com.zero.rainy.db.constants;

/**
 * 数据表通用字段名
 *
 * @author Zero.
 * <p> Created on 2024/9/22 11:05 </p>
 */
public interface EntityColumn {

    /**
     * 主键
     */
    String ID = "id";

    /**
     * 乐观锁标识
     */
    String VERSION = "version";
}
