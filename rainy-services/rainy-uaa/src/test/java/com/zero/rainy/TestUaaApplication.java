package com.zero.rainy;

import com.zero.rainy.core.model.entity.User;
import com.zero.rainy.uaa.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Zero.
 * <p> Created on 2025/3/4 18:13 </p>
 */
@SpringBootTest
public class TestUaaApplication {
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void test() {
        User user = new User();
        user.setUsername("zero9501@outlook.com");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setEmail(user.getUsername());
        user.setNickname("zero");
        Assertions.assertTrue(userService.save(user));
    }
}
