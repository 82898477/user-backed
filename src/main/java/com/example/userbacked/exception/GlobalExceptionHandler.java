package com.example.userbacked.exception;

import com.example.userbacked.common.BaseResponse;
import com.example.userbacked.common.ErrorCode;
import com.example.userbacked.common.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 韩凯翔
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessException(BusinessException e) {
        log.error("BusinessException:{}", e.getMessage());
        return ResultUtil.error(e.getCode(), e.getMessage(), e.getDescription());
    }
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeException(BusinessException e) {
        log.error("runtimeException:{}", e);
        return ResultUtil.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
