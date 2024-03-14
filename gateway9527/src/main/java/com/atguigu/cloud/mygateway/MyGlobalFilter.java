package com.atguigu.cloud.mygateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author WW-20000505
 * @creat 2024-03-11-15:45
 * @description:自定义全局过滤器,对每个路由都有效,即调用每个接口都会执行
 */
@Component
@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {

    private static final String BEGIN_VISIT_TIME = "begin_visit_time";//开始调用方法的时间



    //类似于servlet中的filter
//    ServerWebExchange exchange 相当于application上下文
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put(BEGIN_VISIT_TIME,System.currentTimeMillis());//记录下访问接口的开始时间
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {

            Long beginVisitTime = exchange.getAttribute(BEGIN_VISIT_TIME);
            if(beginVisitTime != null){//调用每个接口都会输出以下信息

                log.info("访问主机:" + exchange.getRequest().getURI().getHost());
                log.info("访问端口:" + exchange.getRequest().getURI().getPort());
                log.info("访问Path:" + exchange.getRequest().getURI().getPath());
                log.info("访问URL请求参数:" + exchange.getRequest().getURI().getRawQuery());
                log.info("接口访问时长:" + (System.currentTimeMillis() - beginVisitTime) + "毫秒");
                log.info("=================================================");
                System.out.println();


            }

        }));// chain.filter(exchange) 表示放行,将请求放给下面的filter

    }

    @Override
    public int getOrder() { //值越小,在过滤器链中的优先级越高,优先执行
        return -1;
    }
}
