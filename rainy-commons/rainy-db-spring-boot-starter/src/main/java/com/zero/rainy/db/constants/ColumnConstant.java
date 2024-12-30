package com.zero.rainy.db.constants;

/**
 * 数据表通用字段名
 *
 * @author Zero.
 * <p> Created on 2024/9/22 11:05 </p>
 */
public interface ColumnConstant {

    /**
     * 主键
     */
    String ID = "id";

    /**
     * 乐观锁标识
     */
    String VERSION = "version";

    /**
     * 创建时间
     */
    String CREATE_TIME = "createTime";

    /**
     * 修改时间
     */
    String UPDATE_TIME = "updateTime";

    /**
     * 创建者
     */
    String CREATOR = "creatUser";

    /**
     * 修改者
     */
    String UPDATER = "updateUser";

    /**
     * 状态
     */
    String STATUS = "status";

    /**
     * 逻辑删除位
     */
    String DELETED = "deleted";


}
