package com.atguigu.cloud.apis;

import com.atguigu.cloud.result.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author WW-20000505
 * @creat 2024-03-13-15:12
 * @description:
 */
@FeignClient(value = "seata-storage-service")
public interface StorageFeignApi {

    /**
     * 扣减库存
     */
    @PostMapping(value = "/storage/decrease")
    ResultData decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
