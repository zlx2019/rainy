package com.zero.rainy;

import com.zero.rainy.core.enums.Gender;
import com.zero.rainy.core.model.entity.User;
import com.zero.rainy.user.service.IUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Zero.
 * <p> Created on 2024/11/4 21:37 </p>
 */
@SpringBootTest
public class UserApplicationTest {
    @Autowired
    private IUserService userService;

    @Test
    public void test() {
        User user = new User().setUsername("zero9501@outlook.com")
                .setPassword("root")
                .setNickname("zero")
                .setGender(Gender.MAN);
        Assertions.assertTrue(userService.save(user));
    }
}
