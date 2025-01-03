package com.zero.rainy.db.ext.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.zero.rainy.core.model.entity.supers.SuperEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 主要用于解决使用 {@link LambdaQueryWrapper} 找不到实体缓存.
 *
 * @author Zero.
 * <p> Created on 2024/10/1 22:07 </p>
 */
@Mapper
public interface SuperEntityMapper<T extends Model<?>> extends SuperMapper<SuperEntity<T>> {

}
