package com.atguigu.cloud.mygateway;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * @author WW-20000505
 * @creat 2024-03-14-20:20
 * @description:
 */
@Component
public class MyGatewayFilterFactory extends AbstractGatewayFilterFactory<MyGatewayFilterFactory.Config> {

    public MyGatewayFilterFactory(){
        super(MyGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(MyGatewayFilterFactory.Config config) {

        return  new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

                ServerHttpRequest request = exchange.getRequest();
                if(request.getQueryParams().containsKey(config.getParam())){
                    return chain.filter(exchange);
                }else{
                    exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                    return exchange.getResponse().setComplete();
                }

            }
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("param");
    }

    @Validated //使类中的校验注解生效
    public static class Config {//config类就是路由断言规则

        @NotEmpty
        @Getter
        @Setter
        private String param;//设定一个值请求中含有该参数才可以放行,值在yaml文件中单一过滤器处设置


    }






}
