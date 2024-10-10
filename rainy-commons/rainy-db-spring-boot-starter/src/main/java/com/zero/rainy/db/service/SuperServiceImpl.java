package com.zero.rainy.db.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zero.rainy.core.constant.Constant;
import com.zero.rainy.core.entity.supers.SuperEntity;
import com.zero.rainy.core.entity.supers.WithLockEntity;
import com.zero.rainy.core.enums.OrderBy;
import com.zero.rainy.core.pojo.rqeuest.PageableQuery;
import com.zero.rainy.core.utils.AssertUtils;
import com.zero.rainy.db.constants.EntityColumn;
import com.zero.rainy.db.mapper.SuperMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * 通用 Service {@link ISuperService} 实现类. 通用抽象方法的实现.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:54 </p>
 */
@Slf4j
public class SuperServiceImpl<M extends SuperMapper<T>,T extends SuperEntity<T>> extends ServiceImpl<M,T> implements ISuperService<T> {


    @Override
    public IPage<T> page(PageableQuery query, Wrapper<T> wrapper) {
        wrapper = Optional.ofNullable(wrapper).orElseGet(this::lambdaWrapper);
        if (wrapper instanceof LambdaQueryWrapper<? extends T> lqw){
            LocalDateTime begin = query.getBegin();
            LocalDateTime end = query.getEnd();
            if (begin != null && end == null){
                // begin <--> Now
                lqw.between(T::getUpdateTime, begin, LocalDateTime.now());
            }else if (end != null && begin != null){
                // begin <--> end
                lqw.between(T::getUpdateTime, begin, end);
            }
            String orderCols = query.getOrderly();
            OrderBy orderBy = query.getOrderBy();
            if (StringUtils.hasText(orderCols) && (!SqlInjectionUtils.check(orderCols))){
                StringBuilder sql = new StringBuilder(ORDER_BY);
                String[] columns = orderCols.split(Constant.DEFAULT_DELIMITER);
                int length = columns.length;
                for (int i = 0; i < length; i++) {
                    sql.append(columns[i]).append(Constant.SPACE).append(orderBy.name());
                    if (i != length - 1) {
                        sql.append(Constant.DEFAULT_DELIMITER);
                    }
                }
                lqw.last(sql.toString());
            }
        }
        return this.page(new Page<>(query.getPage(), query.getPageSize()), wrapper);
    }

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
