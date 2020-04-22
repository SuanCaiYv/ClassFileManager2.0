package com.classfilemanager.org.service;

import com.classfilemanager.org.exception.UnhandledException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SuanCaiYv
 * @time 2020/2/23 下午9:54
 */
@Service
public interface FileService
{
    /**
     * 下载文件
     * @param id 上传者ID
     * @param taskUuid 任务的UUID
     * @param context 异步上下文
     * @throws IOException NA
     */
    void downloadFile(String id, String taskUuid, AsyncContext context) throws IOException;

    /**
     * 上传文件
     * @param id 上传者ID
     * @param taskUuid 任务的UUID
     * @param multipartFile 文件
     * @return NA
     */
    WebAsyncTask<String> uploadFile(String id, String taskUuid, MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response);

    /**
     * 打包下载全部文件
     * @param taskUuid 任务的UUID
     * @param context 异步上下文
     * @throws UnhandledException NA
     * @throws IOException NA
     */
    void downloadAllFile(String taskUuid, AsyncContext context) throws UnhandledException, IOException;
}
