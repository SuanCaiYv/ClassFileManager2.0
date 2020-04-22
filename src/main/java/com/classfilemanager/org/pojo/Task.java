package com.classfilemanager.org.pojo;

import org.springframework.stereotype.Component;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午4:33
 */
@Component
public class Task
{
    private String taskUuid = "";
    private String luncherId = "";
    private Long lunchTime = 0L;
    private String taskName = "";
    private String taskPath = "";
    private String format = "#$*";

    public String getTaskUuid()
    {
        return taskUuid;
    }

    public void setTaskUuid(String taskUuid)
    {
        this.taskUuid = taskUuid;
    }

    public String getLuncherId()
    {
        return luncherId;
    }

    public void setLuncherId(String luncherId)
    {
        this.luncherId = luncherId;
    }

    public Long getLunchTime()
    {
        return lunchTime;
    }

    public void setLunchTime(Long lunchTime)
    {
        this.lunchTime = lunchTime;
    }

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public String getTaskPath()
    {
        return taskPath;
    }

    public void setTaskPath(String taskPath)
    {
        this.taskPath = taskPath;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public Task()
    {
    }
}
