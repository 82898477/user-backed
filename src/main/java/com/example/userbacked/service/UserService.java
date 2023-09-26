package com.example.userbacked.service;

import com.example.userbacked.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.security.NoSuchAlgorithmException;

/**
* @author 82898
* &#064;description  针对表【user(用户表)】的数据库操作Service
* &#064;createDate  2023-09-18 20:20:54
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册校验
     * @param userAccount 账户名
     * @param userPassword  密码
     * @param checkPassword  验证码
     * @return 用户id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * 用户登录校�
     * @param userAccount  要登录的用户名
     * @param userPassword  密码
     * @return  用户对象
     */
    User dologin(String userAccount, String userPassword, HttpServletRequest request);

    User getSafierUser(User originalUser);
    /**
     *用户退出登录
     */
    int userlogout(HttpServletRequest request);
}
