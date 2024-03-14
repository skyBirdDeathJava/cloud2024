package com.atguigu.cloud.apis;

import com.atguigu.cloud.enumeration.ReturnCodeEnum;
import com.atguigu.cloud.result.ResultData;
import org.springframework.stereotype.Component;


@Component
public class PayFeignSentinelApiFallBack implements PayFeignSentinelApi
{
    @Override
    public ResultData getPayByOrderNo(String orderNo)
    {
        return ResultData.error(ReturnCodeEnum.RC500.getCode(),"对方服务宕机或不可用，FallBack服务降级o(╥﹏╥)o");
    }
}