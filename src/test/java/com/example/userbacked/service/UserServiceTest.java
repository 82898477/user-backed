package com.example.userbacked.service;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.userbacked.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author 韩凯翔
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;
    @Test
    void testAddUser(){
        User user = new User();
        user.setUsername("han");
        user.setUserAccount("123");
        user.setAvatarUrl("https://pics1.baidu.com/feed/203fb80e7bec54e79d728717379a045a4ec26a4e.jpeg@f_auto?token=ef87b856eef4f85963145d7b16c8cd32");
        user.setGender(0);
        user.setUserPassword("123");
        user.setEmail("456");
        user.setPhone("asd");
        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(save);

    }

    @Test
    public void testUserRegister() throws NoSuchAlgorithmException {

        // Test case 1: Valid user registration
        String userAccount = "testuser";
        String userPassword = "password123";
        String checkPassword = "password123";
        Long userId = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, userId);

        // Test case 2: Invalid user account
        userAccount = "test";
        userId = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, userId);

        // Test case 3: Invalid user password
        userAccount = "testuser";
        userPassword = "password123";
        checkPassword = "password12";
        userId = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, userId);

    }
}