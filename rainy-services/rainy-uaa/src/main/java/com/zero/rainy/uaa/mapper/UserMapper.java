package com.zero.rainy.uaa.mapper;

import com.zero.rainy.core.model.entity.User;
import com.zero.rainy.db.ext.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Zero.
 * <p> Created on 2025/3/4 13:20 </p>
 */
@Mapper
public interface UserMapper extends SuperMapper<User> {

}
