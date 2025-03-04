package com.zero.rainy.uaa.service;

import com.zero.rainy.core.model.entity.User;
import com.zero.rainy.db.ext.service.ISuperService;

/**
 * @author Zero.
 * <p> Created on 2025/3/4 13:21 </p>
 */
public interface IUserService extends ISuperService<User> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     */
    User findByUsername(String username);
}
