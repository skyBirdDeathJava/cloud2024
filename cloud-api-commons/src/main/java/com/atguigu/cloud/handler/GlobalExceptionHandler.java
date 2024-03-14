package com.atguigu.cloud.handler;

import com.atguigu.cloud.enumeration.ReturnCodeEnum;
import com.atguigu.cloud.result.ResultData;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author WW-20000505
 * @creat 2024-03-05-12:39
 * @description:
 */
@Slf4j
//@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultData<String> exceptionHandler(Exception e){

        log.error("全局异常信息为：{}",e.getMessage());

        return ResultData.error(ReturnCodeEnum.RC500.getCode(), e.getMessage());


    }


}
