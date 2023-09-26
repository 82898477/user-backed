package com.example.userbacked.common;

/**
 * 返回结果类
 * @author 韩凯翔
 */
public class ResultUtil {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, "ok", data);
    }
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getCode(), errorCode.getMessage(), null, errorCode.getDescription());
    }
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse<>(code, message, null, description);
    }
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), message, null, description);
    }


}
