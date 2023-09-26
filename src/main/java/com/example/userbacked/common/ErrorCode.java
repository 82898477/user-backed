package com.example.userbacked.common;

/**
 * @author 韩凯翔
 */
public enum ErrorCode {
    SUCCESS(0, "ok", ""),
    PARAM_ERROR(40000, "参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN(40003, "未登录", ""),
    SYSTEM_ERROR(50000, "系统内部错误", ""),
    NO_AUTH(40002, "无权限", "");


    private final int code;
    private final String message;
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
