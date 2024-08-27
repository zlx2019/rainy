package com.zero.rainy.db.hooks;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 数据库实体自动填充属性.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:22 </p>
 */
@Slf4j
public class EntitySupplementHook implements MetaObjectHandler {

    /**
     * 自动填充五位字段
     * id:         ID
     * createAt: 创建时间
     * updateAt: 创建者ID
     * updateAt: 最后一次修改时间
     * updater: 最后一次修改者ID
     */
    private final String ID = "id";
    private final String CREATE_AT = "createAt";
    private final String UPDATE_AT = "updateAt";
    private final String CREATOR = "creator";
    private final String UPDATER = "updater";

    /**
     * 新增填充
     * @param metaObject 要新增的元对象实体
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, CREATE_AT, LocalDateTime::now , LocalDateTime.class);
        this.strictInsertFill(metaObject, UPDATE_AT, LocalDateTime::now, LocalDateTime.class);
    }

    /**
     * 更新填充
     * @param metaObject 被更新的元对象实体
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 抹除掉之前的值
        metaObject.setValue(UPDATE_AT, null);
        metaObject.setValue(UPDATER, null);
        this.strictUpdateFill(metaObject, UPDATE_AT, LocalDateTime::now, LocalDateTime.class);
    }
}
