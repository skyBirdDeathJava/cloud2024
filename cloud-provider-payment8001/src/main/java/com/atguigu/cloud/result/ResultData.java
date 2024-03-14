package com.atguigu.cloud.result;


import com.atguigu.cloud.enumeration.ReturnCodeEnum;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * @author WW-20000505
 * @creat 2024-03-05-10:18
 * @description:统一返回结果类
 */
@Data
@Accessors(chain = true)
public class ResultData<T> {

    private String code;
    private String message;

    private T data;

    private long timestamp;//接口调用时间,依次判断数据是从缓存取的还是调用接口返回的,调bug排错时可看



    public ResultData(){

        this.timestamp = System.currentTimeMillis();//创建ResultData对象时赋值

    }

    public static <T> ResultData<T> sucess(T data){


        ResultData<T> resultData = new ResultData<>();
        resultData.setData(data);
        resultData.setCode(ReturnCodeEnum.RC200.getCode());
        resultData.setMessage(ReturnCodeEnum.RC200.getMessage());

        return resultData;


    }

    public static <T> ResultData<T> error(String code,String message){//捕捉异常全局异常处理时进行传参

        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMessage(message);
        resultData.setData(null);

        return resultData;


    }





}
