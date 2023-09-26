package com.example.userbacked.model.domain.request;

import lombok.Data;

import java.util.Date;

/**
 * @author 韩凯翔
 */
@Data
public class UserRegisterRequest {

    /**
     * 用户名
     */
    private String userAccount;
    /**
     * 密码
     */
    private String userPassword;
    private String checkPassword;






}
