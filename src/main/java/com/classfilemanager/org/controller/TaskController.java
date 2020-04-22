package com.classfilemanager.org.controller;

import com.classfilemanager.org.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午5:17
 */
@CrossOrigin(origins = "*")
@RestController
public class TaskController
{
    @Autowired
    private TaskService taskService;

    @PostMapping(value = "/lunchTask")
    public WebAsyncTask<String> lunchTask(@RequestParam(name = "id", required = false) String id, @RequestParam("taskName") String taskName,
                                          @RequestParam(name = "format") String format,
                                          HttpServletRequest request, HttpServletResponse response)
    {
        return taskService.lunchTask(id, taskName, format, request, response);
    }

    @PostMapping(value = "/deleteTask")
    public WebAsyncTask<String> deleteTask(@RequestParam(name = "taskUuid") String taskUuid)
    {
        return taskService.deleteTask(taskUuid);
    }
}
