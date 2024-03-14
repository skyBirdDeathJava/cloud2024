package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.apis.AccountFeignApi;
import com.atguigu.cloud.apis.StorageFeignApi;
import com.atguigu.cloud.entities.Order;
import com.atguigu.cloud.mapper.OrderMapper;
import com.atguigu.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author WW-20000505
 * @creat 2024-03-14-11:02
 * @description:
 */

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderMapper orderMapper;


    @Resource//订单微服务通过OpenFeign去远程调用库存微服务,而不是注入库存mapper(一个单体项目是这样)
    //注意每个业务模块都是一个微服务单体项目,只能通过自己的mapper操作自己的数据库,调用其他微服务通过OpenFeign
    private StorageFeignApi storageFeignApi;

    @Resource //库存微服务通过OpenFeign去远程调用账户微服务
    private AccountFeignApi accountFeignApi;
    /**
     * 创建订单
     * @param order
     */
    @Override
    @GlobalTransactional(name = "ww-crate-order",rollbackFor = Exception.class)
    //name属性表示给本次全局事务起名,rollbackFor = Exception.class表示出现异常回滚全局事务
    //在发起此次业务的业务方法(TM)上加上全局事务注解,作用是开启,提交(成功),回滚(发生异常)本次全局事务
    //加全局事务主键表示开启seata AT模式全局事务,解决分布式事务的数据一致性
    public void create(Order order) {



        //Tm向TC申请创建XID,xid是全局事务的标识,在微服务调用链路的上下文中传递 xid = ip+seata端口+全局事务id
        String xid = RootContext.getXID();//RootContext是TC(seata)的上下文,类似于spring的applicationContext
        //1.新建订单,向订单微服务的数据库中订单表中插入一个订单记录
        log.info("-----------开始新建订单:" + "\t" + "xid:" + xid);
        order.setStatus(0);//订单初始状态为0表示未支付
        int row = orderMapper.insertSelective(order);
        
        if(row > 0){


            Order orderDB = orderMapper.selectOne(order);
            log.info("新建订单成功,订单信息为:{}",orderDB);
            System.out.println();

            //2.扣减库存
            log.info("-------> 订单微服务开始调用Storage库存，做扣减count");
            storageFeignApi.decrease(orderDB.getProductId(),orderDB.getCount());
            log.info("-------> 订单微服务结束调用Account账号，做扣减完成");
            System.out.println();

            //3.扣减账号余额
            log.info("-------> 订单微服务开始调用Account账号，做扣减money");
            accountFeignApi.decrease(orderDB.getUserId(), orderDB.getMoney());
            log.info("-------> 订单微服务结束调用Account账号，做扣减完成");
            System.out.println();

            //4.修改订单状态
            log.info("-------> 修改订单状态");
            orderDB.setStatus(1);
            order.setStatus(1);
            //updateByPrimaryKeySelective 根据主键更新属性不为null值的列
            int updateResult = orderMapper.updateByPrimaryKeySelective(orderDB);
            log.info("-------> orderFromDB info: "+ orderDB);
            log.info("-------> 修改订单状态完成"+"\t"+updateResult);
            System.out.println();
        }
        log.info("==================>结束新建订单"+"\t"+"xid_order:" +xid);



    }
}
