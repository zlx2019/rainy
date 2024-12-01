package com.zero.rainy.db.ext.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Arrays;
import java.util.List;

/**
 * 通用Mapper，基于{@link BaseMapper} 进行扩展，所有实体 Mapper 均继承于它.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 18:50 </p>
 */
@SuppressWarnings("all")
public interface SuperMapper<T> extends BaseMapper<T> {

    /**
     * 通用批量新增
     *
     * @param list  批量数据
     * @return      插入的记录数
     */
    int batchInsert(List<T> list);
}