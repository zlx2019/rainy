package com.zero.rainy.sample.model.converts;

import com.zero.rainy.core.model.entity.Sample;
import com.zero.rainy.sample.model.dto.SampleDTO;
import com.zero.rainy.sample.model.vo.SampleVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Sample model converter
 *
 * @author Zero.
 * <p> Created on 2024/12/31 23:50 </p>
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SampleConvert {
    SampleConvert INSTANCE = Mappers.getMapper(SampleConvert.class);

    /**
     * DTO to entity
     */
    Sample toEntity(SampleDTO dto);

    /**
     * entity to DTO
     */
    SampleDTO toDTO(Sample entity);

    /**
     * entity to Vo
     */
    SampleVo toVo(Sample entity);
}
