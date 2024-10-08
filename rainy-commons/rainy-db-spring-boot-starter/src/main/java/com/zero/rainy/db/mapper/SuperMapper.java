package com.zero.rainy.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 通用Mapper，基于{@link BaseMapper} 进行扩展，所有实体 Mapper 均继承于它.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:50 </p>
 */
public interface SuperMapper<T> extends BaseMapper<T> {

}