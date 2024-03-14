package com.atguigu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author WW-20000505
 * @creat ${YEAR}-${MONTH}-${DAY}-${TIME}
 * @description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class Main9001 {
    public static void main(String[] args) {
        SpringApplication.run(Main9001.class,args);
    }
}