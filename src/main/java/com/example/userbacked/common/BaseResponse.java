package com.example.userbacked.common;

import lombok.Data;

/**
 * 通用用返回结果类
 * @author 韩凯翔
 */
@Data
public class BaseResponse<T> {
    private int code;
    private String message;
    private T data;
    private String description;

    public BaseResponse(int code, String message, T data, String description) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.description = description;
    }
    public BaseResponse(int code, String message, T data) {
        this(code, message, data, null);
    }
    public BaseResponse(int code, String message) {
        this(code, message, null);
    }
    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(), errorCode.getMessage(),null,errorCode.getDescription());
    }
}
