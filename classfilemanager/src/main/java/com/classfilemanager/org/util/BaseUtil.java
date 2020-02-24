package com.classfilemanager.org.util;

import com.classfilemanager.org.exception.UnhandledException;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public static String getUuid()
    {
        return UUID.randomUUID().toString().trim().replace("-", "_");
    }

    /**
     * 强制创建文件
     * @param filePath 文件路径
     * @return 文件
     */
    public static Path forceCreateFile(String filePath) throws IOException
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
}
