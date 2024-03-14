package com.atguigu.cloud.handler;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

//解析获取给定HTTP请求中数据
//The origin parser parses request origin (e.g. IP, user, appName) from HTTP request
@Component
public class MyRequestOriginParser implements RequestOriginParser
{
    @Override
    public String parseOrigin(HttpServletRequest request) {
        return request.getParameter("serverName");//获取请求参数serverName
    }
}