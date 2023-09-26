package com.example.userbacked.exception;

import com.example.userbacked.common.ErrorCode;

/**
 * @author 韩凯翔
 */
public class BusinessException extends RuntimeException{
    private final int code;
    private final String description;

    public BusinessException(String message,int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
    public BusinessException(ErrorCode  errorCode){
        this(errorCode.getMessage(),errorCode.getCode(),errorCode.getDescription());
    }

    public BusinessException(ErrorCode  errorCode,String description){
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
