package com.classfilemanager.org.system;

import com.classfilemanager.org.dao.RedisMapper;
import com.classfilemanager.org.pojo.Task;
import com.classfilemanager.org.pojo.User;
import com.classfilemanager.org.util.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author SuanCaiYv
 * @time 2020/2/23 下午9:34
 */
@Component
@EnableScheduling
public class SystemRequired
{

    @Autowired
    private RedisMapper redisMapper;

    /**
     * 任务的保留时间
     */
    private int interval = SystemConstant.INTERVAL*24*60*60*1000;

    /**
     * 执行定时任务
     */
    @Scheduled(fixedRate = 24*60*60*1000)
    public void f() throws IOException
    {
        long lunchTime = System.currentTimeMillis()-interval;
        List<Task> tasks = redisMapper.selectTasksToDelete(lunchTime);
        for (Task task : tasks) {
            BaseUtil.deleteDirectory(task.getTaskPath());
            User user = redisMapper.selectOneUser(task.getLuncherId());
            removeTask(user, task.getTaskUuid());
            redisMapper.deleteTask(task);
        }
    }

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
