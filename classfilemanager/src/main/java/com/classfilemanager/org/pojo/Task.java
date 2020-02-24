package com.classfilemanager.org.pojo;

import org.springframework.stereotype.Component;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午4:33
 */
@Component
public class Task
{
    private String taskUuid;
    private String luncherId;
    private Long lunchTime;
    private String taskName;
    private String taskPath;
    private Integer format;

    @Override
    public String toString()
    {
        return "Task{" +
                "taskUuid='" + taskUuid + '\'' +
                ", luncherId='" + luncherId + '\'' +
                ", lunchTime=" + lunchTime +
                ", taskName='" + taskName + '\'' +
                ", taskPath='" + taskPath + '\'' +
                ", format=" + format +
                '}';
    }

    public Integer getFormat()
    {
        return format;
    }

    public void setFormat(Integer format)
    {
        this.format = format;
    }

    public String getTaskPath()
    {
        return taskPath;
    }

    public void setTaskPath(String taskPath)
    {
        this.taskPath = taskPath;
    }

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public Long getLunchTime()
    {
        return lunchTime;
    }

    public void setLunchTime(Long lunchTime)
    {
        this.lunchTime = lunchTime;
    }

    public String getLuncherId()
    {
        return luncherId;
    }

    public void setLuncherId(String luncherId)
    {
        this.luncherId = luncherId;
    }

    public String getTaskUuid()
    {
        return taskUuid;
    }

    public void setTaskUuid(String taskUuid)
    {
        this.taskUuid = taskUuid;
    }

    public Task()
    {
    }

    public Task(String taskUuid, String luncherId, Long lunchTime, String taskName, String taskPath, Integer format)
    {
        this.taskUuid = taskUuid;
        this.luncherId = luncherId;
        this.lunchTime = lunchTime;
        this.taskName = taskName;
        this.taskPath = taskPath;
        this.format = format;
    }
}
