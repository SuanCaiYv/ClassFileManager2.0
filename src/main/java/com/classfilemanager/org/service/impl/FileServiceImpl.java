package com.classfilemanager.org.service.impl;

import com.alibaba.fastjson.JSON;
import com.classfilemanager.org.config.ThreadExecutor;
import com.classfilemanager.org.dao.RedisMapper;
import com.classfilemanager.org.exception.UnhandledException;
import com.classfilemanager.org.pojo.Task;
import com.classfilemanager.org.pojo.User;
import com.classfilemanager.org.result.ResultBean;
import com.classfilemanager.org.result.TaskMessage;
import com.classfilemanager.org.service.BaseService;
import com.classfilemanager.org.service.FileService;
import com.classfilemanager.org.util.BaseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author SuanCaiYv
 * @time 2020/2/29 上午12:17
 */
@Service
public class FileServiceImpl implements FileService
{
    @Autowired
    private RedisMapper redisMapper;
    @Autowired
    private BaseService baseService;
    @Autowired
    private ThreadExecutor executor;

    @Override
    public void downloadFile(String aId, String taskUuid, AsyncContext context) throws IOException
    {
        Runnable runnable = () -> {
            HttpServletRequest request = (HttpServletRequest) context.getRequest();
            HttpServletResponse response = (HttpServletResponse) context.getResponse();
            String id = BaseUtil.parseSession(request, response, redisMapper, BaseUtil.stringAdd(aId)).split(",")[0];
            Task task = redisMapper.selectOneTask(taskUuid);
            User user = redisMapper.selectOneUser(id);
            Path direPath = Paths.get(task.getTaskPath());
            DirectoryStream<Path> paths = null;
            AtomicReference<Path> targetPath = new AtomicReference<>();
            try {
                paths = Files.newDirectoryStream(direPath);
                paths.forEach(path -> {
                    if (path.getFileName().toString().contains(id) || path.getFileName().toString().contains(user.getName())) {
                        targetPath.set(path);
                    }
                });
                Path file = targetPath.get();
                writeFile(file, response);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                context.complete();
            }
        };
        executor.execute(runnable);
    }

    /**
     * 实现文件保存以及对未完成, 已完成任务的更新
     * @param aId NA
     * @param taskUuid NA
     * @param multipartFile NA
     * @return NA
     */
    @Override
    public WebAsyncTask<String> uploadFile(String aId, String taskUuid, MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response)
    {
        Callable<String> callable = () -> {
            String id = BaseUtil.parseSession(request, response, redisMapper, BaseUtil.stringAdd(aId)).split(",")[0];
            User user = redisMapper.selectOneUser(id);
            Task task = redisMapper.selectOneTask(taskUuid);
            String undoTask = user.getUndoTask();
            String doneTask = user.getDoneTask();
            undoTask = undoTask.replace(taskUuid+",", "");
            doneTask = doneTask.replace(taskUuid+",", "");
            doneTask += taskUuid+",";
            user.setUndoTask(undoTask);
            user.setDoneTask(doneTask);
            redisMapper.updateUser(user);
            String originFileName = multipartFile.getOriginalFilename();
            String fileSuffix = originFileName.substring(originFileName.lastIndexOf("."));
            String newFileName = getFileName(user, task)+fileSuffix;
            Path filePath = Paths.get(task.getTaskPath()+newFileName);
            multipartFile.transferTo(filePath);
            user = redisMapper.selectOneUser(id);
            ResultBean<User, TaskMessage> resultBean = baseService.addMsg(user);
            String json = JSON.toJSONString(resultBean);
            return json;
        };
        WebAsyncTask<String> webAsyncTask = new WebAsyncTask<>(callable);
        return webAsyncTask;
    }

    /**
     * 打包下载
     * @param taskUuid NA
     * @param context NA
     * @throws UnhandledException NA
     * @throws IOException NA
     */
    @Override
    public void downloadAllFile(String taskUuid, AsyncContext context) throws UnhandledException, IOException
    {
        Runnable runnable = () -> {
            Task task = redisMapper.selectOneTask(taskUuid);
            String targetFileName = task.getTaskName()+".zip";
            Path targetFile = Paths.get(targetFileName);
            if (Files.exists(targetFile)) {
                return ;
            }
            try {
                targetFile = BaseUtil.zipFiles(task.getTaskPath());
                HttpServletResponse response = (HttpServletResponse) context.getResponse();
                writeFile(targetFile, response);
            } catch (IOException | UnhandledException e) {
                e.printStackTrace();
            } finally {
                context.complete();
            }
        };
        executor.execute(runnable);
    }

    /**
     * 把文件写入管道流
     * @param file 文件
     * @param response 返回的回复
     * @throws IOException NA
     */
    private void writeFile(Path file, HttpServletResponse response) throws IOException
    {
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        response.reset();
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition","attachment; fileName="+URLEncoder.encode(file.getFileName().toString(), StandardCharsets.UTF_8));
        response.addHeader("Content-Length", "" + Files.size(file));
        outputStream = new BufferedOutputStream(response.getOutputStream());
        bufferedInputStream = new BufferedInputStream(Files.newInputStream(file));
        byte[] bytes = new byte[1024*5];
        int readable = -1;
        while ((readable = bufferedInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, readable);
        }
        outputStream.flush();
        bufferedInputStream.close();
        outputStream.close();
    }

    /**
     * 获取文件名
     * @param user NA
     * @param task NA
     * @return NA
     */
    private String getFileName(User user, Task task)
    {
        String name = task.getFormat();
        name = name.replace("#", user.getName());
        name = name.replace("$", user.getId());
        name = name.replace("*", task.getTaskName());
        return name;
    }
}
