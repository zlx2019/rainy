package com.zero.rainy.user.mapper;

import com.zero.rainy.core.model.entity.User;
import com.zero.rainy.db.ext.mapper.SuperMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * User Mapper
 *
 * @author Zero
 * <p> Created on 2025-01-03 16:51:50 </p>
 */
@Mapper
public interface UserMapper extends SuperMapper<User>{

}