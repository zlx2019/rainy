package com.zero.rainy.user.service.impl;

import com.zero.rainy.db.ext.service.SuperServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.zero.rainy.core.model.entity.User;
import com.zero.rainy.user.mapper.UserMapper;
import com.zero.rainy.user.service.IUserService;
import com.zero.rainy.user.model.converts.UserConvert;
import com.zero.rainy.user.model.vo.UserVo;
import com.zero.rainy.user.model.dto.UserDTO;
import com.zero.rainy.core.exception.RecordNotFoundException;

import java.util.Objects;


/**
 * User Service
 *
 * @author Zero
 * <p> Created on 2025-01-03 16:51:50 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements IUserService {
    private final UserMapper userMapper;
    private final UserConvert userConvert;

    @Override
    public boolean updateById(UserDTO dto){
        if (Objects.isNull(super.getById(dto.getId()))){
            throw new RecordNotFoundException(String.valueOf(dto.getId()));
        }
        return super.updateById(userConvert.toEntity(dto));
    }
}