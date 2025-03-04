package com.zero.rainy.uaa.service.impl;

import com.zero.rainy.security.model.DefaultUserDetails;
import com.zero.rainy.uaa.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 为{@link AuthenticationManager} 提供默认实现：根据用户名从数据库中查询
 *
 * @author Zero.
 * <p> Created on 2025/3/4 16:46 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {
    private final IUserService userService;

    /**
     * find by username
     *
     * @param username username
     * @return {@link DefaultUserDetails}
     * @throws UsernameNotFoundException When the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userService.findByUsername(username))
                .map(DefaultUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + "does not exist"));
    }
}
