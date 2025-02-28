package com.zero.rainy.db.ext.hooks;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zero.rainy.core.enums.supers.Status;
import com.zero.rainy.core.holder.UserContextHolder;
import com.zero.rainy.core.model.entity.supers.SuperEntityExt;
import com.zero.rainy.db.constants.ColumnConstant;
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
public class SupplementEntityHook implements MetaObjectHandler, ColumnConstant {

    /**
     * 新增填充
     * @param metaObject 要新增的元对象实体
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName(ID, IdUtils.getNextId(), metaObject);
        LocalDateTime nowTime = LocalDateTime.now();
        this.strictInsertFill(metaObject, CREATE_AT, LocalDateTime.class, nowTime);
        this.strictInsertFill(metaObject, UPDATE_AT, LocalDateTime.class, nowTime);
        this.strictInsertFill(metaObject, STATUS, Status.class, Status.NORMAL);
        if (metaObject.getOriginalObject() instanceof SuperEntityExt<?>){
            Optional.ofNullable(UserContextHolder.getUserId())
                    .ifPresent(userId-> {
                        this.strictInsertFill(metaObject, CREATOR_BY, Long.class, userId);
                        this.strictInsertFill(metaObject, UPDATER_BY, Long.class, userId);
                    });
        }
    }

    /**
     * 更新填充
     * @param metaObject 被更新的元对象实体
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 抹除掉之前的值
        metaObject.setValue(UPDATE_AT, null);
        this.strictUpdateFill(metaObject, UPDATE_AT, LocalDateTime::now, LocalDateTime.class);
        if (metaObject.getOriginalObject() instanceof SuperEntityExt<?>){
            metaObject.setValue(UPDATER_BY, null);
            this.strictUpdateFill(metaObject, UPDATER_BY, Long.class, UserContextHolder.getUserId());
        }
    }
}
