package com.atguigu.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.atguigu.cloud.apis.PayFeignApi;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.enumeration.ReturnCodeEnum;
import com.atguigu.cloud.result.ResultData;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author WW-20000505
 * @creat 2024-03-05-13:48
 * @description:
 */
@RestController
@Slf4j
@RequestMapping("feign/pay")
public class OrderController {

    @Resource
    private PayFeignApi payFeginApi;

    @PostMapping("add")
    public ResultData addOrder(@RequestBody PayDTO payDTO){

        System.out.println("第一步：模拟本地addOrder新增订单成功(省略sql操作)，第二步：再开启addPay支付微服务远程调用");
        ResultData resultData = payFeginApi.addPay(payDTO);
        return resultData;

    }


    @GetMapping("get/{id}")
    public ResultData getOrder(@PathVariable("id") Integer id){

        System.out.println("-------支付微服务远程调用，按照id查询订单支付流水信息");
        ResultData resultData = null;

        try {

            System.out.println("调用开始----" + DateUtil.now());
            resultData = payFeginApi.getPay(id);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("调用结束---"+DateUtil.now());
            return ResultData.error(ReturnCodeEnum.RC500.getCode(), e.getMessage());
        }

        return resultData;

    }

    /**
     * openfeign天然支持负载均衡演示,默认集成了loadblance负载均衡功能,默认负载均衡算法为轮询
     * 不需要使用loadblance显示给与restTemplate负载均衡能力
     *
     * @return
     */
    @GetMapping("mylb")
    public String  getInfoByConsul(){

        return payFeginApi.mylb();


    }










}
