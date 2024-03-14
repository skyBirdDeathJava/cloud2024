package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.entities.Account;
import com.atguigu.cloud.mapper.AccountMapper;
import com.atguigu.cloud.service.AccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.concurrent.TimeUnit;

/**
 * @author WW-20000505
 * @creat 2024-03-14-12:46
 * @description:
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {


    @Resource
    private AccountMapper accountMapper;
    @Override
    public void decrease(Long userId, Long money) {

        //扣减余额
        log.info("------->account-service中扣减账户余额开始");
        Example whereCondition = new Example(Account.class);
        Example.Criteria criteria = whereCondition.createCriteria();
        criteria.andEqualTo("userId",userId);

        Account accountDB = accountMapper.selectOneByExample(whereCondition);
        accountDB.setUsed(accountDB.getUsed()+money);
        accountDB.setResidue(accountDB.getResidue()-money);
        accountMapper.updateByPrimaryKeySelective(accountDB);

//        myTimeOut(); //超时异常 openfeign调用默认等待响应时间为60秒,超时没收到响应就报错(没设置openfeign重试机制,默认远程调用一次微服务)
//        int age = 10/0; //运行时异常
        log.info("------->account-service中扣减账户余额结束");


    }

    /**
     * 模拟超时异常，全局事务回滚
     */
    private static void myTimeOut()
    {
        try { TimeUnit.SECONDS.sleep(65); } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
