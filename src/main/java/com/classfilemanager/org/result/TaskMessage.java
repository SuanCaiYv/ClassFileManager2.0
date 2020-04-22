package com.classfilemanager.org.result;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author SuanCaiYv
 * @time 2020/2/29 下午10:58
 */
@Component
public class TaskMessage
{
    /**
     * 当前者未提交的
     */
    private List<String> undoTasks;
    /**
     * 当前者已提交的
     */
    private List<String> doneTasks;
    /**
     * 当前者发布的, 若不是班委则为空
     */
    private List<String> lunchTasks;
    /**
     * 当前者的已提交的任务的UUID和文件名映射
     */
    private Map<String, String> taskUuidAndFileName;
    /**
     * taskUuid和任务名映射关系
     */
    private Map<String, String> taskUuidAndTaskName;
    /**
     * 用户id和用户姓名映射关系
     */
    private Map<String, String> idAndName;
    /**
     * 任务的taskUuid和未完成的用户的id的映射关系
     */
    private Map<String, List<String>> taskUuidAndUndoIds;
    /**
     * 任务的taskUuid和已完成的用户的id的映射关系
     */
    private Map<String, List<String>> taskUuidAndDoneIds;
    /**
     * 发布时间
     */
    private Long lunchTime;

    public Map<String, String> getTaskUuidAndFileName()
    {
        return taskUuidAndFileName;
    }

    public void setTaskUuidAndFileName(Map<String, String> taskUuidAndFileName)
    {
        this.taskUuidAndFileName = taskUuidAndFileName;
    }

    public Long getLunchTime()
    {
        return lunchTime;
    }

    public void setLunchTime(Long lunchTime)
    {
        this.lunchTime = lunchTime;
    }

    public List<String> getUndoTasks()
    {
        return undoTasks;
    }

    public void setUndoTasks(List<String> undoTasks)
    {
        this.undoTasks = undoTasks;
    }

    public List<String> getDoneTasks()
    {
        return doneTasks;
    }

    public void setDoneTasks(List<String> doneTasks)
    {
        this.doneTasks = doneTasks;
    }

    public List<String> getLunchTasks()
    {
        return lunchTasks;
    }

    public void setLunchTasks(List<String> lunchTasks)
    {
        this.lunchTasks = lunchTasks;
    }

    public Map<String, String> getTaskUuidAndTaskName()
    {
        return taskUuidAndTaskName;
    }

    public void setTaskUuidAndTaskName(Map<String, String> taskUuidAndTaskName)
    {
        this.taskUuidAndTaskName = taskUuidAndTaskName;
    }

    public Map<String, String> getIdAndName()
    {
        return idAndName;
    }

    public void setIdAndName(Map<String, String> idAndName)
    {
        this.idAndName = idAndName;
    }

    public Map<String, List<String>> getTaskUuidAndUndoIds()
    {
        return taskUuidAndUndoIds;
    }

    public void setTaskUuidAndUndoIds(Map<String, List<String>> taskUuidAndUndoIds)
    {
        this.taskUuidAndUndoIds = taskUuidAndUndoIds;
    }

    public Map<String, List<String>> getTaskUuidAndDoneIds()
    {
        return taskUuidAndDoneIds;
    }

    public void setTaskUuidAndDoneIds(Map<String, List<String>> taskUuidAndDoneIds)
    {
        this.taskUuidAndDoneIds = taskUuidAndDoneIds;
    }
}
