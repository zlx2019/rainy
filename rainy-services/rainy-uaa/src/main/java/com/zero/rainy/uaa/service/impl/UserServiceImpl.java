package com.zero.rainy.uaa.service.impl;

import com.zero.rainy.core.model.entity.User;
import com.zero.rainy.db.ext.service.SuperServiceImpl;
import com.zero.rainy.uaa.mapper.UserMapper;
import com.zero.rainy.uaa.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Zero.
 * <p> Created on 2025/3/4 13:22 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements IUserService {
    private final UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return super.getOneBy(User::getUsername, username);
    }
}
