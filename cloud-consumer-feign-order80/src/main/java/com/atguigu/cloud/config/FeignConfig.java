package com.atguigu.cloud.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WW-20000505
 * @creat 2024-03-08-0:30
 * @description:
 */
@Configuration
public class FeignConfig {

    @Bean
    public Retryer myRetryer()
    {
        return Retryer.NEVER_RETRY; //Feign默认配置,不执行重试策略的

        //最大请求次数为3(初始请求1次+重试2次)，初始与第一次重试间隔时间为100ms，重试间最大间隔时间为1s
//        return new Retryer.Default(100,1,3);
        //重试次数*请求超时时间readTimeout = 总耗时
    }


    //开启feign日志打印输出,默认是不开启 feign中http请求的细节 对feign接口的调用情况进行监控和输出
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

}
