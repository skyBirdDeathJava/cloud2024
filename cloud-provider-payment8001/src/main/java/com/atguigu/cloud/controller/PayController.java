package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.Pay;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.result.ResultData;
import com.atguigu.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author WW-20000505
 * @creat 2024-03-04-15:47
 * @description:
 */
@RestController
@Slf4j
@RequestMapping("pay")
@Tag(name = "支付微服务模块",description = "支付CRUD")
public class PayController {

    @Resource
    private PayService payService;

    @PostMapping("add")
    @Operation(summary = "新增",description = "新增支付流水方法,json串做参数")
    public ResultData<String> addPay(@RequestBody Pay pay){



        System.out.println(pay.toString());

        int add = payService.add(pay);
        return ResultData.sucess("数据插入成功,返回值为"+add);

    }

    @DeleteMapping("del/{id}")
    @Operation(summary = "删除",description = "删除支付流水方法")
    public ResultData<String> delPay(@PathVariable("id") Integer id){

        if(id < 0){
            throw new RuntimeException("id不能为负数");
        }


        int remove = payService.remove(id);
        return ResultData.sucess("数据删除成功,返回值为"+remove);

    }

    @PutMapping("update")
    @Operation(summary = "修改",description = "修改支付流水方法")
    public ResultData<String> updatePay(@RequestBody PayDTO payDTO){

        Pay pay = new Pay();
        BeanUtils.copyProperties(payDTO,pay);
        int update = payService.update(pay);
        return ResultData.sucess("数据更新成功,返回值为"+update);

    }

    @GetMapping("get/{id}")
    @Operation(summary = "按照ID查流水",description = "查询支付流水方法")
    public ResultData<Pay> getPay(@PathVariable("id") Integer id)  {

        //暂停62秒钟线程,故意写bug,测试出openfeign的默认调用超时时间
        try {
            TimeUnit.SECONDS.sleep(62);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Pay pay = payService.getById(id);
        return ResultData.sucess(pay);

    }

    @GetMapping("getAllPay")
    @Operation(summary = "无参",description = "查询全部支付流水方法")
    public ResultData<List<Pay>> getAllPay(){

        return ResultData.sucess(payService.getAll());

    }

    @Value("${server.port}")
    private String port;

    @GetMapping("get/info")
    public String getInfoByConsul(@Value("${atguigu.info}") String atguiguInfo){

        return "atguiguInfo" + atguiguInfo + "\t" + "port" + port;

    }
}
