package com.atguigu.cloud.mygateway;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.AfterRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 自定义断言,需求：自定义配置会员等级userType,按照钻/金/银和yaml配置中设置的会员等级匹配,判断是否可以访问
 */
@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {


    public MyRoutePredicateFactory() {
        super(MyRoutePredicateFactory.Config.class);
    }

    @Validated //使类中的校验注解生效
    public static class Config {//config类就是路由断言规则

        @NotEmpty
        @Getter
        @Setter
        private String userType;//值在yaml文件中断言处设置


    }

    @Override
    public List<String> shortcutFieldOrder() {//支持yaml中断言shortcut配置
        return Collections.singletonList("userType");
    }

    @Override
    public Predicate<ServerWebExchange> apply(MyRoutePredicateFactory.Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                //检查request的参数里面(userType要为地址栏请求参数)，userType是否为指定的值，符合配置就通过
                //http://localhost:9527/pay/gateway/get/1?username=gold
                String userType = serverWebExchange.getRequest().getQueryParams().getFirst("userType");

                if (userType == null) return false;

                //如果说参数存在，就和config的数据进行比较 config.getUserType()获取在yaml中断言处设置的值
                if (userType.equals(config.getUserType())) {
                    return true;
                }

                return false;
            }
        };
    }



}
 