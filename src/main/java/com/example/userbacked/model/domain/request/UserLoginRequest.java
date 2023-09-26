package com.example.userbacked.model.domain.request;

import lombok.Data;

/**
 * @author 韩凯翔
 */
@Data
public class UserLoginRequest {
    private String userAccount;
    /**
     * 密码
     */
    private String userPassword;
}
