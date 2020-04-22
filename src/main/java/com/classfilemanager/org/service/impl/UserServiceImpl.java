package com.classfilemanager.org.service.impl;

import com.alibaba.fastjson.JSON;
import com.classfilemanager.org.config.ThreadExecutor;
import com.classfilemanager.org.dao.RedisMapper;
import com.classfilemanager.org.dao.VerCodeMapper;
import com.classfilemanager.org.pojo.User;
import com.classfilemanager.org.result.ResultBean;
import com.classfilemanager.org.result.TaskMessage;
import com.classfilemanager.org.service.BaseService;
import com.classfilemanager.org.service.UserService;
import com.classfilemanager.org.util.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author SuanCaiYv
 * @time 2020/2/27 下午7:41
 */
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private RedisMapper redisMapper;
    @Autowired
    private VerCodeMapper verCodeMapper;
    @Autowired
    private BaseService baseService;
    @Autowired
    private ThreadExecutor executor;

    @Override
    public WebAsyncTask<String> loginIn(String aId, String aPassword, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        Callable<String> callable = () -> {
            String[] strings = BaseUtil.parseSession(request, response, redisMapper, BaseUtil.stringAdd(aId, aPassword)).split(",");
            String id = strings[0];
            String password = strings[1];
            User user = redisMapper.selectOneUser(id);
            ResultBean<User, TaskMessage> resultBean = new ResultBean<>();
            if (user == null) {
                resultBean.setCode(ResultBean.NO_USER);
                resultBean.setData(null);
                resultBean.setAddData(null);
                resultBean.setMsg("no user");
            }
            else if (!user.getPassword().equals(password)) {
                resultBean.setCode(ResultBean.PASSWORD_ERROR);
                resultBean.setData(null);
                resultBean.setAddData(null);
                resultBean.setMsg("password error");
            }
            else {
                resultBean = baseService.addMsg(user);
            }
            String json = JSON.toJSONString(resultBean);
            return json;
        };
        WebAsyncTask<String> task = new WebAsyncTask<>(3000, callable);
        return task;
    }

    @Override
    public WebAsyncTask<String> updateUser(String id, String password, String name, String qq, String email)
    {
        Callable<String> callable = () -> {
            User user = redisMapper.selectOneUser(id);
            user.setPassword(password);
            user.setName(name);
            user.setQq(qq);
            user.setEmail(email);
            redisMapper.updateUser(user);
            ResultBean<String, Void> resultBean = new ResultBean<>();
            resultBean.setMsg("success");
            resultBean.setMsg("更新信息成功");
            String json = JSON.toJSONString(resultBean);
            return json;
        };
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(callable);
        return webAsyncTask;
    }

    @Override
    public WebAsyncTask<String> sendVerificationEmail(String aId, HttpServletRequest request, HttpServletResponse response)
    {
        Callable<String> callable = () -> {
            String id = BaseUtil.parseSession(request, response, redisMapper, BaseUtil.stringAdd(aId)).split(",")[0];
            User user = redisMapper.selectOneUser(id);
            String code = BaseUtil.sendMail(user.getEmail());
            verCodeMapper.insert(id, code);
            ResultBean<String, Void> resultBean = new ResultBean<>();
            resultBean.setMsg("success");
            resultBean.setData(code);
            String json = JSON.toJSONString(resultBean);
            return json;
        };
        Runnable runnable = () -> {
            String id = BaseUtil.parseSession(request, response, redisMapper, BaseUtil.stringAdd(aId)).split(",")[0];
            verCodeMapper.delete(id);
        };
        executor.schedule(runnable);
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(callable);
        return webAsyncTask;
    }

    @Override
    public WebAsyncTask<String> modifyPassword(String id, String password)
    {
        Callable<String> callable = () -> {
            User user = redisMapper.selectOneUser(id);
            user.setPassword(password);
            redisMapper.updateUser(user);
            ResultBean<String, Void> resultBean = new ResultBean<>();
            resultBean.setMsg("success");
            resultBean.setData("修改成功");
            String json = JSON.toJSONString(resultBean);
            return json;
        };
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(callable);
        return webAsyncTask;
    }

}
