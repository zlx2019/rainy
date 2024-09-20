package com.zero.rainy.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.rainy.core.entity.supers.SuperEntity;
import com.zero.rainy.db.utils.VersionChecker;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * 通用 Service {@link ISuperService} 实现类. 通用抽象方法的实现.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:54 </p>
 */
@Slf4j
public class SuperServiceImpl<M extends BaseMapper<T>,T extends SuperEntity<T>> extends ServiceImpl<M,T> implements ISuperService<T> {

    @Override
    public boolean updateByLock(T entity) {
        VersionChecker<?> checker = new VersionChecker<>(entity.getClass());
        if (!checker.hasVersion()){
            return super.updateById(entity);
        }
        Field field = checker.getVersionField();
        new QueryWrapper<T>()
                .select("version");
        while (!this.updateById(entity)){
            // TODO
        }
        return false;
    }
}
