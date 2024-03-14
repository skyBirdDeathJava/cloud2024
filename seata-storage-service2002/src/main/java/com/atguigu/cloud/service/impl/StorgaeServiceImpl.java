package com.atguigu.cloud.service.impl;

import com.atguigu.cloud.apis.AccountFeignApi;
import com.atguigu.cloud.entities.Storage;
import com.atguigu.cloud.mapper.StorageMapper;
import com.atguigu.cloud.service.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author WW-20000505
 * @creat 2024-03-14-12:15
 * @description:
 */
@Service
@Slf4j
public class StorgaeServiceImpl implements StorageService {

    @Resource
    private StorageMapper storageMapper;

    @Override
    public void decrease(Long productId, Integer count) {

        //扣减库存
        log.info("------->storage-service中扣减库存开始");
        //设置where条件
        Example whereCondition = new Example(Storage.class);
        Example.Criteria criteria = whereCondition.createCriteria();
        criteria.andEqualTo("productId",productId);

        Storage storageDB = storageMapper.selectOneByExample(whereCondition);//根据设置的where条件查询
        storageDB.setUsed(storageDB.getUsed()+count);
        storageDB.setResidue(storageDB.getResidue()-count);
        storageMapper.updateByPrimaryKeySelective(storageDB);
        log.info("------->storage-service中扣减库存结束");

    }
}
