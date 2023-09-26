package com.example.userbacked.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.userbacked.common.ErrorCode;
import com.example.userbacked.exception.BusinessException;
import com.example.userbacked.service.UserService;
import com.example.userbacked.model.domain.User;
import com.example.userbacked.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

import static com.example.userbacked.contant.UserConstant.USER_LOGIN_STATUS;

/**
 * @author 82898
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2023-09-18 20:20:54
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
//        1.用户在前端输入账户和密码、以及校验码 (todo)
//    2.校验用户的账户、密码、校验密码，是否符合要求
//        1.非空
        if (userAccount == null || userPassword == null || checkPassword == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "参数为空");
        }
//        2.
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账户小于4位");
        }
//        3.吧
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码小于 8 位");
        }
        //        5.账户不包含特殊字符
//        if (userAccount.contains("@") || userAccount.contains("#") || userAccount.contains("$") || userAccount.contains("%") || userAccount.contains("^") || userAccount.contains("&") || userAccount.contains("*") || userAccount.contains("(") || userAccount.contains(")")
        String regex = "^[a-zA-Z0-9_]+$";
        Pattern pattern = Pattern.compile(regex);
        boolean isMatch = pattern.matcher(userAccount).matches();
        if (!isMatch) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "包含特殊字符");
        }
//        4.账户不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        Long count = userMapper.selectCount(userQueryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账户不能重复");
        }

        //    6.密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码和校验密码不一致");
        }
//    3.对密码进行加密(客码千万不要直接以明文存储到数据库中
        /**
         * 使用SHA-256算法对密码进行加密
         * @param password 待加密的密码
         * @return 加密后的密码
         */
        String encryption = encryption(userPassword);

//        4.向数据库插入用户数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(userPassword);
        boolean save = this.save(user);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "插入数据失败");
        }
        return user.getId();
    }

    @Override
    public User dologin(String userAccount, String userPassword, HttpServletRequest request) {
        if (userAccount == null || userPassword == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "参数为空");
        }
//        2.账户的话 不小于4位
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账户小于4位");
        }
//        3.密码就 不小于 8 位吧
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "密码小于8位");
        }
        //        5.账户不包含特殊字符
//        if (userAccount.contains("@") || userAccount.contains("#") || userAccount.contains("$") || userAccount.contains("%") || userAccount.contains("^") || userAccount.contains("&") || userAccount.contains("*") || userAccount.contains("(") || userAccount.contains(")")
        String regex = "^[a-zA-Z0-9_]+$";
        Pattern pattern = Pattern.compile(regex);
        boolean isMatch = pattern.matcher(userAccount).matches();
        if (!isMatch) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "包含特殊字符");
        }
        //密码加密
        String encryption = encryption(userPassword);
        //查询用户是否存在
        QueryWrapper<User> eq = new QueryWrapper<User>().eq("userAccount", userAccount)
                .eq("userPassword", userPassword);
        User user = userMapper.selectOne(eq);
        //如用户不存在
        if (user == null) {
            log.info("");
            throw new BusinessException(ErrorCode.PARAM_ERROR, "账号与密码不匹配");
        }
//用户脱敏
        User safierUser = getSafierUser(user);
        //记录用户的登录状态
        request.getSession().setAttribute(USER_LOGIN_STATUS, safierUser);
        return safierUser;
    }

    private String encryption(String userPassword) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            byte[] digest = instance.digest(userPassword.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            //加密后的密码
            return String.valueOf(sb);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用户脱敏
     * @param originalUser
     * @return
     */
    @Override
    public User getSafierUser(User originalUser) {
        if (originalUser == null) {
            return null;
        }
        User saferUser = new User();
        saferUser.setId(originalUser.getId());
        saferUser.setUsername(originalUser.getUsername());
        saferUser.setUserAccount(originalUser.getUserAccount());
        saferUser.setAvatarUrl(originalUser.getAvatarUrl());
        saferUser.setGender(originalUser.getGender());
        saferUser.setEmail(originalUser.getEmail());
        saferUser.setUserRole(originalUser.getUserRole());
        saferUser.setUserStatus(originalUser.getUserStatus());
        saferUser.setPhone(originalUser.getPhone());
        saferUser.setCreateTime(originalUser.getCreateTime());
        return saferUser;
    }

    @Override
    public int userlogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATUS);
        return 1;
    }
}




