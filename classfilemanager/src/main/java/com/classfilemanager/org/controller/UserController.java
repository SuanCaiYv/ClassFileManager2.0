package com.classfilemanager.org.controller;

import com.classfilemanager.org.dao.UserMapper;
import com.classfilemanager.org.pojo.User;
import com.classfilemanager.org.result.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午5:16
 */
@Controller
@ResponseBody
public class UserController
{
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/loginIn")
    public ResultBean<User> loginIn(@RequestParam(value = "id") String id, @RequestParam(value = "password") String password)
    {
        User user = userMapper.selectOne(id);
        ResultBean<User> resultBean = new ResultBean<>();
        if (user == null) {
            resultBean.setCode(ResultBean.NO_USER);
            resultBean.setData(null);
            resultBean.setMsg("no user");
            return resultBean;
        }
        else if (!user.getPassword().equals(password)) {
            resultBean.setCode(ResultBean.PASSWORD_ERROR);
            resultBean.setData(null);
            resultBean.setMsg("password error");
            return resultBean;
        }
        else {
            resultBean.setCode(ResultBean.SUCCESS);
            resultBean.setData(user);
            resultBean.setMsg("success");
            return resultBean;
        }
    }
}
