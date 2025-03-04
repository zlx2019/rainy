package com.zero.rainy.uaa.service.impl;

import com.zero.rainy.core.model.entity.User;
import com.zero.rainy.security.model.DefaultUserDetails;
import com.zero.rainy.uaa.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 用户查询
 *
 * @author Zero.
 * <p> Created on 2025/3/4 16:46 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUserDetailsServiceImpl implements UserDetailsService {
    private final IUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return new DefaultUserDetails(user);
    }
}
