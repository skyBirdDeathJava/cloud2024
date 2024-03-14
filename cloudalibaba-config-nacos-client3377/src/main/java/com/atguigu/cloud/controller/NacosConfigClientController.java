package com.atguigu.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther zzyy
 * @create 2023-11-27 15:51
 */
@RestController
@RefreshScope //在控制器类加入@RefreshScope注解使当前类下的配置数据支持Nacos的动态刷新功能。
public class NacosConfigClientController
{
    @Value("${config.info}") //该配置数据在配置中心中全局配置文件中设置,从其读取
    private String configInfo;

    @GetMapping("/config/info")
    public String getConfigInfo() {
        return configInfo;
    }
}