package com.classfilemanager.org.service.impl;

import com.classfilemanager.org.dao.RedisMapper;
import com.classfilemanager.org.pojo.Task;
import com.classfilemanager.org.pojo.User;
import com.classfilemanager.org.result.ResultBean;
import com.classfilemanager.org.result.TaskMessage;
import com.classfilemanager.org.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author SuanCaiYv
 * @time 2020/3/2 上午12:17
 */
@Service
public class BaseServiceImpl implements BaseService
{
    @Autowired
    private RedisMapper redisMapper;

    @Override
    public ResultBean<User, TaskMessage> addMsg(User user) throws IOException
    {
        ResultBean<User, TaskMessage> resultBean = new ResultBean<>();
        resultBean.setMsg("success");
        resultBean.setData(user);
        TaskMessage taskMessage = new TaskMessage();
        List<String> undoTasks = new ArrayList<>(50);
        List<String> doneTasks = new ArrayList<>(50);
        List<String> allTasks = new ArrayList<>(50);
        List<String> lunchTasks = new ArrayList<>(20);
        List<String> ids = new ArrayList<>(50);
        Map<String, String> taskUuidAndFileName = new HashMap<>();
        Map<String, String> taskUuidAndTaskName = new HashMap<>();
        Map<String, String> idAndName = new HashMap<>();
        Map<String, List<String>> taskUuidAndUndoIds = new HashMap<>();
        Map<String, List<String>> taskUuidAndDoneIds = new HashMap<>();
        getUndoTasks(user, undoTasks);
        getDoneTasks(user, doneTasks);
        allTasks.addAll(undoTasks);
        allTasks.addAll(doneTasks);
        getLunchTasks(user, lunchTasks);
        getIds(user, ids);
        getTaskUuidAndFileName(user, doneTasks, taskUuidAndFileName);
        getTaskUuidAndTaskName(allTasks, taskUuidAndTaskName);
        getIdAndName(ids, idAndName);
        getTaskUuidAndUndoIds(lunchTasks, taskUuidAndUndoIds);
        getTaskUuidAndDoneIds(lunchTasks, taskUuidAndDoneIds);
        taskMessage.setUndoTasks(undoTasks);
        taskMessage.setDoneTasks(doneTasks);
        taskMessage.setLunchTasks(lunchTasks);
        taskMessage.setTaskUuidAndFileName(taskUuidAndFileName);
        taskMessage.setTaskUuidAndTaskName(taskUuidAndTaskName);
        taskMessage.setIdAndName(idAndName);
        taskMessage.setTaskUuidAndUndoIds(taskUuidAndUndoIds);
        taskMessage.setTaskUuidAndDoneIds(taskUuidAndDoneIds);
        resultBean.setAddData(taskMessage);
        return resultBean;
    }

    private void getUndoTasks(User user, List<String> undoTasks)
    {
        String[] strings = user.getUndoTask().split(",");
        if (!"".equals(strings[0])) {
            undoTasks.addAll(Arrays.asList(strings));
        }
    }

    private void getDoneTasks(User user, List<String> doneTasks)
    {
        String[] strings = user.getDoneTask().split(",");
        if (!"".equals(strings[0])) {
            doneTasks.addAll(Arrays.asList(strings));
        }
    }

    private void getLunchTasks(User user, List<String> lunchTasks)
    {
        List<Task> tasks = redisMapper.selectTasksById(user.getId());
        if (tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                lunchTasks.add(task.getTaskUuid());
            }
        }
    }

    /**
     * 同班的用户的ID
     * @param user 当前者
     * @param ids id列表
     */
    private void getIds(User user, List<String> ids)
    {
        List<User> users = redisMapper.selectUsersSameClassroom(user);
        if (users != null && users.size() > 0) {
            for (User u : users) {
                ids.add(u.getId());
            }
        }
    }

    /**
     * 当前者的已提交的任务的UUID和文件名映射
     * @param user 当前者
     * @param taskUuids 已完成的任务列表
     * @param taskUuidAndFileName NA
     * @throws IOException NA
     */
    private void getTaskUuidAndFileName(User user, List<String> taskUuids, Map<String, String> taskUuidAndFileName) throws IOException
    {
        if (taskUuids != null && taskUuids.size() > 0) {
            for (String taskUuid : taskUuids) {
                Task task = redisMapper.selectOneTask(taskUuid);
                Path dire = Paths.get(task.getTaskPath());
                DirectoryStream<Path> paths = Files.newDirectoryStream(dire);
                paths.forEach(p -> {
                    if (p.getFileName().toString().contains(user.getId()) || p.getFileName().toString().contains(user.getName())) {
                        taskUuidAndFileName.put(taskUuid, p.getFileName().toString());
                    }
                });
            }
        }
    }

    private void getIdAndName(List<String> ids, Map<String, String> idAndName)
    {
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                User user = redisMapper.selectOneUser(id);
                idAndName.put(id, user.getName());
            }
        }
    }

    private void getTaskUuidAndTaskName(List<String> taskUuids, Map<String, String> taskUuidAndTaskName)
    {
        if (taskUuids != null && taskUuids.size() > 0) {
            for (String taskUuid : taskUuids) {
                taskUuidAndTaskName.put(taskUuid, redisMapper.selectOneTask(taskUuid).getTaskName());
            }
        }
    }

    private void getTaskUuidAndUndoIds(List<String> taskUuids, Map<String, List<String>> taskUuidAndUndoIds)
    {
        if (taskUuids != null && taskUuids.size() > 0) {
            String temp = taskUuids.get(0);
            String id = redisMapper.selectOneTask(temp).getLuncherId();
            List<User> users = redisMapper.selectUsersSameClassroom(redisMapper.selectOneUser(id));
            for (String taskUuid : taskUuids) {
                List<String> list = new ArrayList<>(50);
                for (User user : users) {
                    if (user.getUndoTask().contains(taskUuid)) {
                        list.add(user.getId());
                    }
                }
                if (list.size() > 0) {
                    taskUuidAndUndoIds.put(taskUuid, list);
                }
            }
        }
    }

    private void getTaskUuidAndDoneIds(List<String> taskUuids, Map<String, List<String>> taskUuidAndDoneIds)
    {
        if (taskUuids != null && taskUuids.size() > 0) {
            String temp = taskUuids.get(0);
            List<User> users = redisMapper.selectUsersSameClassroom(redisMapper.selectOneUser(redisMapper.selectOneTask(temp).getLuncherId()));
            for (String taskUuid : taskUuids) {
                List<String> list = new ArrayList<>(50);
                for (User user : users) {
                    if (user.getDoneTask().contains(taskUuid)) {
                        list.add(user.getId());
                    }
                }
                if (list.size() > 0) {
                    taskUuidAndDoneIds.put(taskUuid, list);
                }
            }
        }
    }
}
