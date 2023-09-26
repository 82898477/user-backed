package com.example.userbacked.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.userbacked.common.BaseResponse;
import com.example.userbacked.common.ErrorCode;
import com.example.userbacked.common.ResultUtil;
import com.example.userbacked.model.domain.User;
import com.example.userbacked.model.domain.request.UserLoginRequest;
import com.example.userbacked.model.domain.request.UserRegisterRequest;
import com.example.userbacked.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;
import static com.example.userbacked.contant.UserConstant.ADMIN_ROLE;
import static com.example.userbacked.contant.UserConstant.USER_LOGIN_STATUS;

/**
 * 用户接口
 *
 * @author 韩凯翔
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return ResultUtil.error(ErrorCode.NOT_LOGIN);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (userAccount == null || userPassword == null || checkPassword == null) {
            return ResultUtil.error(ErrorCode.PARAM_ERROR);
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtil.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtil.error(ErrorCode.NOT_LOGIN);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (userAccount == null || userPassword == null) {
            return ResultUtil.error(ErrorCode.PARAM_ERROR);
        }
        User user1 = userService.dologin(userAccount, userPassword, request);
        return ResultUtil.success(user1);
    }

    //查找用户
    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest request) {
        //仅管理员可以查询
        if (!isadmin(request)) {
            return ResultUtil.error(ErrorCode.NO_AUTH);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> list = userService.list(queryWrapper);
        List<User> collect = list.stream().map(user -> userService.getSafierUser(user)).collect(Collectors.toList());
        return ResultUtil.success(collect);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        //仅管理员可以查询
        if (!isadmin(request)){
            return ResultUtil.error(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            return ResultUtil.error(ErrorCode.NULL_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtil.success(b);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userLoginStatus = request.getSession().getAttribute(USER_LOGIN_STATUS);
        if (userLoginStatus == null) {
            return ResultUtil.error(ErrorCode.NOT_LOGIN);
        }
        User currentUser = (User) userLoginStatus;
        User userNow = userService.getById(currentUser.getId());
        User safierUser = userService.getSafierUser(userNow);
        return ResultUtil.success(safierUser);
    }

    /**
     * 用户退出登录
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userlogout(HttpServletRequest request) {
        if (request == null) {
            return ResultUtil.error(ErrorCode.NULL_ERROR);
        }
        int userlogout = userService.userlogout(request);
        return ResultUtil.success(userlogout);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isadmin(HttpServletRequest request) {
        //仅管理员可以查询
        Object userLoginStatus = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user = (User) userLoginStatus;
        if (user == null || (user.getUserRole() != ADMIN_ROLE)) {
            return false;
        }

        return true;
    }


}
