package com.classfilemanager.org.controller;

import com.classfilemanager.org.exception.UnhandledException;
import com.classfilemanager.org.service.FileService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午5:17
 */
@CrossOrigin(origins = "*")
@RestController
public class FileController
{
    @Autowired
    private FileService fileService;

    @PostMapping(value = "/file/upload")
    public WebAsyncTask<String> fileUpload(@RequestParam(name = "id", required = false) String id, @RequestParam(name = "taskUuid") String taskUuid,
                                           @RequestParam(name = "file") MultipartFile multipartFile,
                                           HttpServletRequest request, HttpServletResponse response)
    {
        return fileService.uploadFile(id, taskUuid, multipartFile, request, response);
    }

    @PostMapping(value = "/file/download")
    public void fileDownload(@RequestParam(name = "id", required = false) String id, @RequestParam(name = "taskUuid") String taskUuid,
                             @NotNull HttpServletRequest request) throws IOException
    {
        AsyncContext context = request.startAsync();
        fileService.downloadFile(id, taskUuid, context);
    }

    @RequestMapping(value = "/file/downloadAll")
    public void fileDownloadAll(@RequestParam(name = "taskUuid") String taskUuid, @NotNull HttpServletRequest request) throws IOException, UnhandledException
    {
        AsyncContext context = request.startAsync();
        fileService.downloadAllFile(taskUuid, context);
    }
}
