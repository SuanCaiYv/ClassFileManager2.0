package com.classfilemanager.org.service.impl;

import com.alibaba.fastjson.JSON;
import com.classfilemanager.org.dao.RedisMapper;
import com.classfilemanager.org.pojo.Task;
import com.classfilemanager.org.pojo.User;
import com.classfilemanager.org.result.ResultBean;
import com.classfilemanager.org.result.TaskMessage;
import com.classfilemanager.org.service.BaseService;
import com.classfilemanager.org.service.TaskService;
import com.classfilemanager.org.system.SystemConstant;
import com.classfilemanager.org.util.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author SuanCaiYv
 * @time 2020/2/29 下午10:54
 */
@Service
public class TaskServiceImpl implements TaskService
{
    @Autowired
    private RedisMapper redisMapper;
    @Autowired
    private BaseService baseService;

    @Override
    public WebAsyncTask<String> lunchTask(String aId, String taskName, String format, HttpServletRequest request, HttpServletResponse response)
    {
        Callable<String> callable = () -> {
            String id = BaseUtil.parseSession(request, response, redisMapper, BaseUtil.stringAdd(aId)).split(",")[0];
            User user = redisMapper.selectOneUser(id);
            String taskUuid = BaseUtil.getUuid();
            String taskPath = SystemConstant.BASE_PATH+user.getDepartment()+"/"+user.getGrade()+"/"+user.getClassroom()+"/"+taskUuid+"/";
            long lunchTime = System.currentTimeMillis();
            Task task = new Task();
            task.setTaskUuid(taskUuid);
            task.setTaskName(taskName);
            task.setTaskPath(taskPath);
            task.setLuncherId(id);
            task.setLunchTime(lunchTime);
            task.setFormat(format);
            redisMapper.insertTask(task);
            addUndoTasksToAll(user, taskUuid);
            user = redisMapper.selectOneUser(id);
            ResultBean<User, TaskMessage> resultBean = baseService.addMsg(user);
            String json = JSON.toJSONString(resultBean);
            return json;
        };
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(callable);
        return webAsyncTask;
    }

    @Override
    public WebAsyncTask<String> deleteTask(String taskUuid)
    {
        Callable<String> callable = () -> {
            String id = redisMapper.selectOneTask(taskUuid).getLuncherId();
            User user = redisMapper.selectOneUser(id);
            removeTask(user, taskUuid);
            redisMapper.deleteTask(taskUuid);
            user = redisMapper.selectOneUser(id);
            ResultBean<User, TaskMessage> resultBean = baseService.addMsg(user);
            String json = JSON.toJSONString(resultBean);
            return json;
        };
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(callable);
        return webAsyncTask;
    }

    /**
     * 向同班的所有人添加未提交任务
     * @param user NA
     * @param taskUuid NA
     */
    private void addUndoTasksToAll(User user, String taskUuid)
    {
        List<User> users = redisMapper.selectUsersSameClassroom(user);
        for (User u : users) {
            String undoTask = u.getUndoTask();
            undoTask += taskUuid+",";
            u.setUndoTask(undoTask);
            redisMapper.updateUser(u);
        }
    }

    /**
     * 从班级所有人的任务列表删除此任务
     * @param user NA
     * @param taskUuid NA
     */
    private void removeTask(User user, String taskUuid)
    {
        List<User> users = redisMapper.selectUsersSameClassroom(user);
        for (User u : users) {
            String undoTask = u.getUndoTask();
            String doneTask = u.getDoneTask();
            undoTask = undoTask.replace(taskUuid+",", "");
            doneTask = doneTask.replace(taskUuid+",", "");
            u.setUndoTask(undoTask);
            u.setDoneTask(doneTask);
            redisMapper.updateUser(u);
        }
    }

}
