package com.classfilemanager.org.controller;

import com.classfilemanager.org.dao.RedisMapper;
import com.classfilemanager.org.util.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 测试用
 * @author SuanCaiYv
 * @time 2020/2/22 下午4:36
 */
@RestController
public class DevController
{
    @Autowired
    private RedisMapper redisMapper;

    @RequestMapping(value = "/qwer")
    public void f1(@RequestParam(name = "name", required = false) String name, HttpServletRequest request, HttpServletResponse response)
    {
        System.out.println(BaseUtil.parseSession(request, response, redisMapper, BaseUtil.stringAdd(name)));
    }
}
  