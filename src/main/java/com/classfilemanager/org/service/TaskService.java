package com.classfilemanager.org.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SuanCaiYv
 * @time 2020/2/23 下午9:55
 */
@Service
public interface TaskService
{
    /**
     * 发布任务
     * @param id 发布者ID
     * @param taskName 任务名
     * @param format 形式
     * @return NA
     */
    WebAsyncTask<String> lunchTask(String id, String taskName, String format, HttpServletRequest request, HttpServletResponse response);

    /**
     * 删除任务
     * @param taskUuid 任务的UUID
     * @return NA
     */
    WebAsyncTask<String> deleteTask(String taskUuid);
}
