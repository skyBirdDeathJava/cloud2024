package com.atguigu.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class RateLimitController
{
    @GetMapping("/rateLimit/byUrl")
    public String byUrl()
    {
        return "按rest地址限流测试OK";
    }


    @GetMapping("/rateLimit/byResource")
    @SentinelResource(value = "byResourceSentinelResource",blockHandler = "handleException")
    //value是sentinel控制台中的资源名,唯一即可,其实是给该handler起别名,否则handler的资源名默认为访问地址/rateLimit/byResource
    //blockHandler是sentinel限流后的自定义的限流提示方法,默认的限流提示为Blocked By Sentinel(flow limiting)
    public String byResource()
    {
        return "按资源名称SentinelResource限流测试OK";
    }
    /*
    限流提示方法声明: public 形参需要和byResource方法中的相同后面加上BlockException exception,如果byResource方法没有形参,则形参仅为BlockException exception
     */
    public String handleException(BlockException exception)
    {
        return "服务不可用@SentinelResource启动"+"\t"+"o(╥﹏╥)o";
    }


    //sentinelresource配置+自定义限流提示+程序异常fallback服务降级
    //违背了对该资源配置的流控规则,sentinel进行限流会执行限流提示方法,当没有违背流控规则时如果出现异常,会执行fallback降级方法,两者可以共存
    //当既违背了流控规则,又发生了异常时,优先执行限流提示方法
    @GetMapping("/rateLimit/doAction/{p1}")
    @SentinelResource(value = "doActionSentinelResource",
            blockHandler = "doActionBlockHandler", fallback = "doActionFallback")
    public String doAction(@PathVariable("p1") Integer p1) {
        if (p1 == 0){
            throw new RuntimeException("p1等于零直接异常");
        }
        return "doAction";
    }

    public String doActionBlockHandler(@PathVariable("p1") Integer p1,BlockException e){
        log.error("sentinel配置自定义限流了:{}", e);
        return "sentinel配置自定义限流了";
    }

    /*
    服务降级方法:public 形参是和doAction完全一样的形参列表后面加上Throwable e ,如果doAction没有形参,则参数仅为Throwable e
     */
    public String doActionFallback(@PathVariable("p1") Integer p1, Throwable e){
        log.error("程序逻辑异常了:{}", e);
        return "程序逻辑异常了"+"\t"+e.getMessage();
    }
}