package com.atguigu.cloud.service;

import com.atguigu.cloud.entities.Pay;

import java.util.List;

/**
 * @author WW-20000505
 * @creat 2024-03-04-15:34
 * @description:
 */
public interface PayService {

    int add(Pay pay);
    int remove(Integer id);

    int update(Pay pay);
    Pay geyById(Integer id);

    List<Pay> getAll();

}
