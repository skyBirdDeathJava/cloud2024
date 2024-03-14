package com.atguigu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author WW-20000505
 * @creat ${YEAR}-${MONTH}-${DAY}-${TIME}
 * @description:
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Main8401 {
    public static void main(String[] args) {
        SpringApplication.run(Main8401.class,args);
    }
}