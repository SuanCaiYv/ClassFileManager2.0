package com.classfilemanager.org.dao;

import com.classfilemanager.org.pojo.Task;
import com.classfilemanager.org.pojo.User;
import com.classfilemanager.org.util.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author SuanCaiYv
 * @time 2020/3/3 下午11:24
 */
@Component
public class RedisMapper
{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 是否发生人数变动
     */
    private boolean isUserChanged = false;
    /**
     * 是否发生任务变动
     */
    private boolean isTaskChanged = false;

    /**
     * 添加数据, 并在Redis中设置为30分钟后过期
     *
     * @param user NA
     */
    public void insertUser(User user)
    {
        userMapper.insert(user);
        String userStr = null;
        try {
            userStr = BaseUtil.objectToString(user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assert userStr != null;
        redisTemplate.opsForValue().set(user.getId(), userStr, 30, TimeUnit.MINUTES);
        isUserChanged = true;
    }

    /**
     * 删除数据, Redis和数据库同时删除
     *
     * @param user NA
     */
    public void deleteUser(User user)
    {
        userMapper.delete(user);
        redisTemplate.delete(user.getId());
        isUserChanged = true;
    }

    /**
     * 同上
     *
     * @param id NA
     */
    public void deleteUser(String id)
    {
        userMapper.delete(id);
        redisTemplate.delete(id);
        isUserChanged = true;
    }

    /**
     * 更新数据, Redis和数据库同时更新
     *
     * @param user NA
     */
    public void updateUser(User user)
    {
        userMapper.update(user);
        String userStr = null;
        try {
            userStr = BaseUtil.objectToString(user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assert userStr != null;
        redisTemplate.opsForValue().set(user.getId(), userStr, 30, TimeUnit.MINUTES);
    }

    /**
     * 先从Redis选取, 若不存在, 则从数据库选取, 并添加到Redis
     * @param id NA
     * @return NA
     */
    public User selectOneUser(String id)
    {
        User user = null;
        String userStr = (String) redisTemplate.opsForValue().get(id);
        if (userStr == null || "".equals(userStr)) {
            user = userMapper.selectOne(id);
            try {
                userStr = BaseUtil.objectToString(user);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            assert userStr != null;
            redisTemplate.opsForValue().set(user.getId(), userStr, 30, TimeUnit.MINUTES);
        } else {
            try {
                user = (User) BaseUtil.stringToObject(userStr, User.class);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    /**
     * 同上的选取策略
     *
     * @param user NA
     * @return NA
     */
    public List<User> selectUsersSameClassroom(User user)
    {
        String key = user.getDepartment() + "_" + user.getGrade() + "_" + user.getClassroom();
        Set<?> set = redisTemplate.opsForSet().members(key);
        if (set == null || isUserChanged || set.size() == 0) {
            List<User> users = userMapper.selectSameClassroom(user);
            for (User u : users) {
                redisTemplate.opsForSet().add(key, u.getId());
            }
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
            isUserChanged = false;
            return users;
        } else {
            List<User> users = new ArrayList<>(50);
            for (Object o : set) {
                String id = (String) o;
                try {
                    users.add(selectOneUser(id));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return users;
        }
    }

    /**
     * 同步插入数据
     * @param task NA
     */
    public void insertTask(Task task)
    {
        taskMapper.insert(task);
        String taskStr = null;
        try {
            taskStr = BaseUtil.objectToString(task);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assert taskStr != null;
        redisTemplate.opsForValue().set(task.getTaskUuid(), taskStr, 30, TimeUnit.MINUTES);
        isTaskChanged = true;
    }

    /**
     * 同步删除
     * @param task NA
     */
    public void deleteTask(Task task)
    {
        taskMapper.delete(task);
        redisTemplate.delete(task.getTaskUuid());
        isTaskChanged = true;
    }

    /**
     * 同步删除
     * @param taskUuid NA
     */
    public void deleteTask(String taskUuid)
    {
        taskMapper.delete(taskUuid);
        redisTemplate.delete(taskUuid);
        isTaskChanged = true;
    }

    /**
     * 同步更新
     * @param task NA
     */
    public void updateTask(Task task)
    {
        taskMapper.update(task);
        try {
            redisTemplate.opsForValue().set(task.getTaskUuid(), BaseUtil.objectToString(task), 30, TimeUnit.MINUTES);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 选取策略同上
     * @param taskUuid NA
     * @return NA
     */
    public Task selectOneTask(String taskUuid)
    {
        String taskStr = (String) redisTemplate.opsForValue().get(taskUuid);
        Task task = null;
        if (taskStr == null || "".equals(taskStr)) {
            task = taskMapper.selectOne(taskUuid);
            try {
                redisTemplate.opsForValue().set(task.getTaskUuid(), BaseUtil.objectToString(task), 30, TimeUnit.MINUTES);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                task = (Task) BaseUtil.stringToObject(taskStr, Task.class);
            } catch (NoSuchMethodException | InvocationTargetException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return task;
    }

    /**
     * 选取策略同上, 选择某一班委发布的所有任务
     * @param luncherId NA
     * @return NA
     */
    public List<Task> selectTasksById(String luncherId)
    {
        String key = "T"+luncherId;
        Set<?> set = redisTemplate.opsForSet().members(key);
        if (set == null || isTaskChanged || set.size() == 0) {
            List<Task> tasks = taskMapper.selectById(luncherId);
            for (Task task : tasks) {
                redisTemplate.opsForSet().add(key, task.getTaskUuid());
            }
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
            isTaskChanged = false;
            return tasks;
        }
        else {
            List<Task> tasks = new ArrayList<>(20);
            for (Object o : set) {
                String taskUuid = (String) o;
                tasks.add(selectOneTask(taskUuid));
            }
            return tasks;
        }
    }

    /**
     * 因为执行频率低, 所以不封装
     * @param lunchTime NA
     * @return NA
     */
    public List<Task> selectTasksToDelete(Long lunchTime)
    {
        return taskMapper.selectToDelete(lunchTime);
    }

    /**
     * 起到Session功能, 设置键值对
     * @param key NA
     * @param object NA
     */
    public void setAttribute(String key, Object object)
    {
        redisTemplate.opsForValue().set(key, object, 30, TimeUnit.MINUTES);
    }

    public Object getAttribute(String key)
    {
        return redisTemplate.opsForValue().get(key);
    }
}
