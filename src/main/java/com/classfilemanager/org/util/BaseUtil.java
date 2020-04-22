package com.classfilemanager.org.util;

import com.classfilemanager.org.dao.RedisMapper;
import com.classfilemanager.org.exception.UnhandledException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.Nullable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

/**
 * 工具类, 提供各种基础工具
 * @author SuanCaiYv
 * @time 2020/2/22 下午4:38
 */
public class BaseUtil
{

    /**
     * 返回UUID
     * @return UUID
     */
    @NotNull
    public static String getUuid()
    {
        return UUID.randomUUID().toString().trim().replace("-", "_");
    }

    /**
     * 强制创建文件
     * @param filePath 文件路径
     * @return 文件
     */
    @NotNull
    public static Path forceCreateFile(@NotNull String filePath) throws IOException
    {
        Path path = Paths.get(filePath.substring(0, filePath.lastIndexOf("/")));
        Path file = Paths.get(filePath);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        if (!Files.exists(file)) {
            Files.createFile(file);
        }
        return file;
    }

    /**
     * 压缩文件夹内的文件, 不适用文件夹套文件夹的情况
     * @param dire 目录
     * @return 目标zip文件
     */
    @NotNull
    public static Path zipFiles(String dire) throws IOException, UnhandledException
    {
        Path zipPath = forceCreateFile(dire);
        int index = dire.lastIndexOf("/");
        if (index == -1) {
            throw new UnhandledException("Directory Path Error!", "BaseUtil.zipFiles()");
        }
        String direName = dire.substring(index+1);
        Path zipFile = forceCreateFile(dire.substring(0, index+1) + direName + ".zip");
        DirectoryStream<Path> stream = Files.newDirectoryStream(zipPath);
        ZipArchiveOutputStream outputStream = new ZipArchiveOutputStream(Files.newOutputStream(zipFile));
        stream.forEach(p -> {
            ZipArchiveEntry entry = new ZipArchiveEntry(p.toFile(), p.getFileName().toString());
            InputStream inputStream = null;
            try {
                outputStream.putArchiveEntry(entry);
                inputStream = new BufferedInputStream(Files.newInputStream(p));
                byte[] bytes = new byte[1024*5];
                int readable = -1;
                while ((readable = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, readable);
                }
                outputStream.closeArchiveEntry();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        outputStream.finish();
        outputStream.close();
        return zipFile;
    }

    /**
     * 删除文件夹及其内部文件
     * @param direName 文件夹名
     * @throws IOException NA
     */
    public static void deleteDirectory(String direName) throws IOException
    {
        Path path = Paths.get(direName);
        FileUtils.deleteDirectory(path.toFile());
    }

    /**
     * 进行邮箱验证
     * @param targetEmail 目标邮箱
     * @throws EmailException NA
     * @return 验证码
     */
    public static String sendMail(String targetEmail) throws EmailException
    {
        int val = new Random().nextInt() % 999999;
        String verificationCode = String.format("%06d", val);
        Email email = new SimpleEmail();
        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("shuizhuyv461200@gmail.com", "lkbujxyoneeubwzn"));
        email.setSSLOnConnect(true);
        email.setFrom("shuizhuyv461200@gmail.com");
        email.setSubject("Verification Mail");
        email.setMsg("This is a verification mail. \nPlease take your verification code: "+verificationCode+" in less than 15 min.");
        email.addTo(targetEmail);
        email.send();
        return verificationCode;
    }

    /**
     * 根据Cookie处理Session
     * @param request 请求
     * @param response 回复
     * @param param 原始的数据参数(id, password)
     * @return 包含信息的串, 若找不到就返回空白串
     */
    public static String parseSession(@NotNull HttpServletRequest request, HttpServletResponse response, RedisMapper redisMapper, String param)
    {
        Cookie[] cookies = request.getCookies();
        // 若不存在, 则创建Cookie并添加, 以及添加Session
        if (cookies == null) {
            String uuid = BaseUtil.getUuid();
            Cookie cookie = new Cookie("sessionId", uuid);
            response.addCookie(cookie);
            redisMapper.setAttribute(uuid, param);
            return param;
        }
        else {
            // 若存在则找寻
            for (Cookie cookie : cookies) {
                // 找到了
                if ("sessionId".equals(cookie.getName())) {
                    String parameter = (String) redisMapper.getAttribute(cookie.getValue());
                    // 进行对比
                    if (!isEmpty(param)) {
                        if (param.equals(parameter)) {
                            return parameter;
                        }
                        // 若不同, 则更新
                        else {
                            redisMapper.setAttribute(cookie.getValue(), param);
                            return param;
                        }
                    }
                    // 传参为空
                    else {
                        return parameter;
                    }
                }
            }
            // 没找到
            String uuid = BaseUtil.getUuid();
            Cookie cookie = new Cookie("sessionId", uuid);
            response.addCookie(cookie);
            redisMapper.setAttribute(uuid, param);
            return param;
        }
    }

    private static boolean isEmpty(@NotNull String str)
    {
        int count = 0;
        for (int i = 0; i < str.length(); ++ i) {
            if (str.charAt(i) == ',') {
                ++ count;
            }
        }
        return count == str.length();
    }

    /**
     * String类型相加
     * @param strings 参数
     * @return 相加的结果
     */
    @NotNull
    public static String stringAdd(String... strings)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : strings) {
            if (s == null) {
                stringBuilder.append(",");
            }
            else {
                stringBuilder.append(s).append(",");
            }
        }
        return stringBuilder.toString().trim();
    }

    /**
     * 把类转成字符串
     * @param object 对象
     * @return 字符串
     * @throws IllegalAccessException NA
     */
    @NotNull
    public static String objectToString(Object object) throws IllegalAccessException
    {
        if (object == null) {
            return "";
        }
        Class<?> tClass = object.getClass();
        Field[] fields = tClass.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        for (Field field : fields) {
            field.setAccessible(true);
            stringBuilder.append(field.get(object).toString()).append("&");
        }
        return stringBuilder.toString();
    }

    /**
     * 把字符串转成类
     * @param string 字符串
     * @param tClass 目标类型
     * @return 实例对象
     * @throws NoSuchMethodException NA
     * @throws IllegalAccessException NA
     * @throws InstantiationException NA
     * @throws InvocationTargetException NA
     */
    @Nullable
    public static Object stringToObject(String string, Class<?> tClass) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException
    {
        if ("".equals(string)) {
            return null;
        }
        Object object = tClass.getConstructor().newInstance();
        String[] strings = string.split("&");
        Field[] fields = tClass.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            Class<?> clazz = field.getType();
            Object o = clazz.getConstructor(String.class).newInstance(strings[i]);
            field.set(object, o);
            ++ i;
        }
        return object;
    }
}
