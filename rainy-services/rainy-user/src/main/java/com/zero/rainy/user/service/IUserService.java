package com.zero.rainy.user.service;

import com.zero.rainy.core.model.entity.User;
import com.zero.rainy.user.model.dto.UserDTO;
import com.zero.rainy.db.ext.service.ISuperService;


/**
 * User Service
 *
 * @author Zero
 * <p> Created on 2025-01-03 16:51:50 </p>
 */
public interface IUserService extends ISuperService<User> {

    /**
     * 根据ID修改记录
    */
    boolean updateById(UserDTO dto);
}