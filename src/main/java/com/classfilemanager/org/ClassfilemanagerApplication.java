package com.classfilemanager.org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ClassfilemanagerApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ClassfilemanagerApplication.class, args);
    }

}
