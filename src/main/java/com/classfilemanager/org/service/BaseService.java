package com.classfilemanager.org.service;

import com.classfilemanager.org.pojo.User;
import com.classfilemanager.org.result.ResultBean;
import com.classfilemanager.org.result.TaskMessage;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author SuanCaiYv
 * @time 2020/2/23 下午9:54
 */
@Service
public interface BaseService
{
    /**
     * 生成ResultBean, 用于返回给前端
     * @param user 当前者
     * @return NA
     */
    ResultBean<User, TaskMessage> addMsg(User user) throws IOException;
}
