package com.zero.rainy.core.model.convert;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * 通用转换器
 *  - 基于Spring环境
 *  - 忽略 null
 *
 * @author Zero.
 * <p> Created on 2025/1/1 01:40 </p>
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BaseConvert {

}
