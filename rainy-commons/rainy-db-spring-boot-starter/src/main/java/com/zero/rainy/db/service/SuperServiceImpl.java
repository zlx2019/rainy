package com.zero.rainy.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.rainy.core.entity.supers.SuperEntity;
import com.zero.rainy.core.entity.supers.WithLockEntity;
import com.zero.rainy.core.utils.AssertUtils;
import com.zero.rainy.db.constants.EntityColumn;
import com.zero.rainy.db.mapper.SuperMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * 通用 Service {@link ISuperService} 实现类. 通用抽象方法的实现.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:54 </p>
 */
@Slf4j
public class SuperServiceImpl<M extends SuperMapper<T>,T extends SuperEntity<T>> extends ServiceImpl<M,T> implements ISuperService<T> {

    @Override
    public boolean lockUpdate(T entity) {
        AssertUtils.nonNull(entity, "update entity is null");
        if (!(entity instanceof WithLockEntity<?>)){
            return super.updateById(entity);
        }
        QueryWrapper<T> wrapper = new QueryWrapper<T>().select(EntityColumn.VERSION).eq(EntityColumn.ID, entity.getId());
        while (!super.updateById(entity)){
            // reload latest version
            T latest = super.getOne(wrapper);
            if (Objects.isNull(latest)){
                return Boolean.FALSE;
            }
            if (latest instanceof WithLockEntity<?> lockEntity){
                ((WithLockEntity<?>) entity).setVersion(lockEntity.getVersion());
            }
        }
        return Boolean.TRUE;
    }
}
