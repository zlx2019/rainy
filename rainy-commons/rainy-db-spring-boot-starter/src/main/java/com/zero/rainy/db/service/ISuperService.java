package com.zero.rainy.db.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zero.rainy.core.entity.supers.SuperEntity;
import com.zero.rainy.core.pojo.Pages;
import com.zero.rainy.core.pojo.rqeuest.PageableQuery;

/**
 * 通用 Service 基于{@link IService}进行扩展。
 * 增加一些通用抽象方法方法。所有实体 Service 均继承于它.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:52 </p>
 */
public interface ISuperService<T extends SuperEntity<T>> extends IService<T> {



    default Pages<T> pages(PageableQuery query){
        return Pages.of(this.page(query, null));
    };
    default Pages<T> pages(PageableQuery query, Wrapper<T> wrapper){
        return Pages.of(this.page(query, wrapper));
    };
    default <V> Pages<V> pages(PageableQuery query, Wrapper<T> wrapper, Class<V> voClass) {
        return Pages.of(this.page(query, wrapper), voClass);
    }

    default IPage<T> page(PageableQuery query){
        return this.page(query, null);
    }
    /**
     * 通用分页查询
     *
     * @param query     分页参数.
     * @param wrapper   查询条件.
     */
     IPage<T> page(PageableQuery query, Wrapper<T> wrapper);


    /**
     * 通过乐观锁更新.
     * @param entity 要更新的数据实体.
     * @return       是否更新成功.
     */
    boolean lockUpdate(T entity);


    default QueryWrapper<T> wrapper() {
        return Wrappers.query();
    }
    default LambdaQueryWrapper<T> lambdaWrapper() {
        return Wrappers.lambdaQuery();
    }
}
