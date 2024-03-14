package com.atguigu.cloud.controller;

import com.atguigu.cloud.apis.PayFeignApi;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * Resilience4j CircuitBreaker 的例子
 */
@RestController
public class OrderCircuitController
{
    @Resource
    private PayFeignApi payFeignApi;

    /*
     * @CircuitBreaker注解起到保险丝的作用,保护调用者
     * name = "cloud-payment-service" 指定要调用的微服务名,yaml配置文件instances属性值相同
     * fallbackMethod = "myCircuitFallback" 表示被调用的微服务熔断后,调用的兜底的服务降级方法名
     *
     *
     */
    @GetMapping(value = "/feign/pay/circuit/{id}")
    @CircuitBreaker(name = "cloud-payment-service", fallbackMethod = "myCircuitFallback")
    public String myCircuitBreaker(@PathVariable("id") Integer id)
    {
        return payFeignApi.myCircuit(id);
    }

    //myCircuitFallback就是服务降级后的兜底处理方法,不要让用户等待并立刻返回一个友好提示fallback
    public String myCircuitFallback(Integer id,Throwable t) {//服务降级方法的形参要包括上面的方法的形参
        // 这里是容错处理逻辑，返回备用结果
        return "myCircuitFallback，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~";
    }


    /**
     *(船的)舱壁,隔离
     * @param id
     * @return
     */
    /*
    @Bulkhead表示舱壁隔离,限制被调用微服务的调用者的并发执行的数量,即限制请求数
    name = "cloud-payment-service" 指定要调用的微服务名,yaml配置文件instances属性值相同
    fallbackMethod = "myBulkheadFallback" 表示被调用的微服务熔断后,调用的兜底的服务降级方法名
    type = Bulkhead.Type.SEMAPHORE 指定舱壁隔离的类型是使用信号量实现,当多于最大并发数量的请求再来时会被阻塞


     */
    @GetMapping(value = "/feign/pay/bulkhead/{id}")
    @Bulkhead(name = "cloud-payment-service",fallbackMethod = "myBulkheadFallback",type = Bulkhead.Type.SEMAPHORE)
    public String myBulkhead(@PathVariable("id") Integer id)
    {
        return payFeignApi.myBulkhead(id);
    }
    public String myBulkheadFallback(Throwable t)
    {
        return "myBulkheadFallback，隔板超出最大数量限制，系统繁忙，请稍后再试-----/(ㄒoㄒ)/~~";
    }


    @GetMapping(value = "/feign/pay/ratelimit/{id}")
    @RateLimiter(name = "cloud-payment-service",fallbackMethod = "myRatelimitFallback")
    public String myRatelimit(@PathVariable("id") Integer id)
    {
        return payFeignApi.myRatelimit(id);
    }
    public String myRatelimitFallback(Integer id,Throwable t)
    {
        return "你被限流了，禁止访问/(ㄒoㄒ)/~~";
    }

}