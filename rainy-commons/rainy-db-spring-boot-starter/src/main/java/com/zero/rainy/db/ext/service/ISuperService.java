package com.zero.rainy.db.ext.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zero.rainy.core.model.entity.supers.SuperEntity;
import com.zero.rainy.core.model.PageResult;
import com.zero.rainy.core.model.PageableQuery;

import java.util.List;

/**
 * 通用 Service 基于{@link IService}进行扩展。
 * 增加一些通用抽象方法方法。所有实体 Service 均继承于它.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:52 </p>
 */
@SuppressWarnings("all")
public interface ISuperService<T extends SuperEntity<T>> extends IService<T> {
    String ORDER_BY = " ORDER BY ";

    default PageResult<T> pages(PageableQuery query) {
        return PageResult.of(this.page(query, null));
    }


    default PageResult<T> pages(PageableQuery query, Wrapper<T> wrapper) {
        return PageResult.of(this.page(query, wrapper));
    }

    default <V> PageResult<V> pages(PageableQuery query, Class<V> voClass) {
        return PageResult.of(this.page(query, null), voClass);
    }

    default <V> PageResult<V> pages(PageableQuery query, Wrapper<T> wrapper, Class<V> voClass) {
        return PageResult.of(this.page(query, wrapper), voClass);
    }

    default IPage<T> page(PageableQuery query) {
        return this.page(query, null);
    }

    /**
     * 通用分页查询
     *
     * @param query   分页参数.
     * @param wrapper 查询条件.
     */
    IPage<T> page(PageableQuery query, Wrapper<T> wrapper);


    /**
     * 查询所有记录，映射为Vo
     */
    <V> List<V> list(Wrapper<T> wrp, Class<V> voClass);

    default <V> List<V> list(Class<V> voClass) {
        return this.list(null, voClass);
    }

    /**
     * 通过乐观锁更新.
     *
     * @param entity 要更新的数据实体.
     * @return 是否更新成功.
     */
    boolean lockUpdate(T entity);

    /**
     * 批量新增
     *
     * @param list 实体对象集合
     * @return 是否全部插入成功
     */
    boolean batchSave(List<T> list);

    default boolean batchSave(T... items) {
        return this.batchSave(List.of(items));
    }


    default QueryWrapper<T> wrapper() {
        return Wrappers.query();
    }

    default LambdaQueryWrapper<T> lambdaWrapper() {
        return Wrappers.lambdaQuery();
    }
}
