package com.zero.rainy.db.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zero.rainy.core.entity.supers.SuperEntity;

/**
 * 通用 Service 基于{@link IService}进行扩展。
 * 增加一些通用抽象方法方法。所有实体 Service 均继承于它.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:52 </p>
 */
public interface ISuperService<T extends SuperEntity<T>> extends IService<T> {

    boolean lockUpdate(T entity);
}
