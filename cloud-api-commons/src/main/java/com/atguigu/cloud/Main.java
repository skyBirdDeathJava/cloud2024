package com.atguigu.cloud;

import java.time.ZonedDateTime;

/**
 * @author WW-20000505
 * @creat ${YEAR}-${MONTH}-${DAY}-${TIME}
 * @description:
 */
public class Main {
    public static void main(String[] args) {
        ZonedDateTime zbj = ZonedDateTime.now(); // 默认时区
        System.out.println(zbj);
    }
}