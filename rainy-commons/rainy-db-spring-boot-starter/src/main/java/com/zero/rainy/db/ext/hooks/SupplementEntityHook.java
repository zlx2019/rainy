package com.zero.rainy.db.ext.hooks;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zero.rainy.core.holder.UserContextHolder;
import com.zero.rainy.db.constants.EntityColumn;
import com.zero.rainy.db.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 数据库实体自动填充属性.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:22 </p>
 */
@Slf4j
public class SupplementEntityHook implements MetaObjectHandler {

    /**
     * id:       ID主键
     * status    状态
     * createAt: 创建时间
     * updateAt: 更新时间
     * creator:  创建者
     * updater:  更新者
     */
    private final String ID = EntityColumn.ID;
    private final String CREATE_TIME = "createTime";
    private final String UPDATE_TIME = "updateTime";
    private final String CREATOR = "creatUser";
    private final String UPDATER = "updateUser";

    /**
     * 新增填充
     * @param metaObject 要新增的元对象实体
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName(ID, IdUtils.getNextId(), metaObject);
        LocalDateTime nowTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, CREATE_TIME, LocalDateTime.class, nowTime);
        this.strictInsertFill(metaObject, UPDATE_TIME, LocalDateTime.class, nowTime);
        Optional.ofNullable(UserContextHolder.getUser())
                .ifPresent(user-> {
//                    this.strictInsertFill(metaObject, CREATOR, Long.class, Long.valueOf(user));
//                    this.strictInsertFill(metaObject, UPDATER, Long.class, Long.valueOf(user));
                });
    }

    /**
     * 更新填充
     * @param metaObject 被更新的元对象实体
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 抹除掉之前的值
        metaObject.setValue(UPDATE_TIME, null);
        this.strictUpdateFill(metaObject, UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
    }
}
