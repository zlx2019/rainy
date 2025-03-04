package com.zero.rainy.security.model;

import com.zero.rainy.core.enums.supers.Status;
import com.zero.rainy.core.model.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 用户认证及权限信息, 实现于{@link UserDetails}
 *
 * @author Zero.
 * <p> Created on 2025/3/4 13:28 </p>
 */
@Data
public class DefaultUserDetails  implements UserDetails {

    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

    /**
     * 用户状态
     */
    private Status status;

    public DefaultUserDetails(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.status = user.getStatus();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO 暂定权限
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getStatus().equals(Status.NORMAL);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getStatus().equals(Status.NORMAL);
    }
}
