package com.classfilemanager.org;

import com.classfilemanager.org.dao.UserMapper;
import com.classfilemanager.org.exception.UnhandledException;
import com.classfilemanager.org.pojo.User;
import com.classfilemanager.org.util.BaseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@SpringBootTest
class ClassfilemanagerApplicationTests
{
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Test
    void contextLoads()
    {
        ;
    }

}
