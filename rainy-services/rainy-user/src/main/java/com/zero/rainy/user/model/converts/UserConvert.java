package com.zero.rainy.user.model.converts;

import com.zero.rainy.core.model.entity.User;
import com.zero.rainy.user.model.vo.UserVo;
import com.zero.rainy.user.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * User Convert
 *
 * @author Zero
 * <p> Created on 2025-01-03 16:51:50 </p>
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserConvert{
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    /**
     * DTO to entity
     */
    User toEntity(UserDTO dto);

    /**
     * entity to DTO
     */
    UserDTO toDTO(User entity);

    /**
     * entity to Vo
     */
    UserVo toVo(User entity);

    /**
     * DTO to Vo
     */
    UserVo toVo(UserDTO dto);
}