package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.result.ResultData;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author WW-20000505
 * @creat 2024-03-05-13:48
 * @description:
 */
@RestController
@Slf4j
@RequestMapping("consumer/pay")
public class OrderController {

//    private static final String PaymentSer_URL = "http://localhost:8001";
      private static final String PaymentSer_URL = "http://cloud-payment-service";//解决硬编码问题

    @Resource
    private RestTemplate restTemplate;


    @PostMapping("add")
    public ResultData addOrder(@RequestBody PayDTO payDTO){

        return restTemplate.postForObject(PaymentSer_URL+"/pay/add",payDTO,ResultData.class);

    }

    @GetMapping("get/{id}")
    public ResultData getOrder(@PathVariable("id") Integer id){


        return restTemplate.getForObject(PaymentSer_URL+"/pay/get/"+id,ResultData.class,1);
    }

    @DeleteMapping("del/{id}")
    public ResultData removeOrder(@PathVariable("id") Integer id){

        restTemplate.delete(PaymentSer_URL+"/pay/remove/"+id,id);
        return ResultData.sucess(null);

    }


    @GetMapping("get/info")
    public String getInfoByConsul(){

        return restTemplate.getForObject(PaymentSer_URL+"/pay/get/info",String.class);

    }


    /**
     * 使用discoveryClient获取所有上线的服务列表
     */
    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/consumer/discovery")
    public String discovery()
    {
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            System.out.println(element);
        }

        System.out.println("===================================");

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance element : instances) {
            System.out.println(element.getServiceId()+"\t"+element.getHost()+"\t"+element.getPort()+"\t"+element.getUri());
        }

        return instances.get(0).getServiceId()+":"+instances.get(0).getPort();

    }



}
