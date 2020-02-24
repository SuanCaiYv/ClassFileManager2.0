package com.classfilemanager.org;

import com.classfilemanager.org.exception.UnhandledException;
import com.classfilemanager.org.util.BaseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootTest
class ClassfilemanagerApplicationTests
{

    @Test
    void contextLoads() throws IOException, UnhandledException
    {
        Path path = Paths.get("/home/joker/Pictures/chrome");
        Path pathDire = BaseUtil.zipFiles(path.toString());
        System.out.println(pathDire.toString());
    }

}
