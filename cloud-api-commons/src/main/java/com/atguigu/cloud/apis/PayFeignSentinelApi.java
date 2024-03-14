package com.atguigu.cloud.apis;


import com.atguigu.cloud.result.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "nacos-payment-provider",fallback = PayFeignSentinelApiFallBack.class)
//fallback = PayFeignSentinelApiFallBack.class 表示微服务调用者通过openfeign调用接口中所有的方法出现异常情况(调用失败,宕机不可用,调用超时,程序发生异常),则统一服务降级
//都执行PayFeignSentinelApiFallBack类的降级方法(实现该接口的重写后的方法,方法内写降级语言)
public interface PayFeignSentinelApi
{
    @GetMapping("/pay/nacos/get/{orderNo}")
    public ResultData getPayByOrderNo(@PathVariable("orderNo") String orderNo);
}
 