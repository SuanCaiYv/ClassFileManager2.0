package com.classfilemanager.org;

import com.classfilemanager.org.pojo.User;
import com.classfilemanager.org.util.BaseUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author SuanCaiYv
 * @time 2020/3/4 上午12:00
 */
public class Main
{
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException, ClassNotFoundException
    {
        ;
    }
    public static void f(@NotNull String msg)
    {
        System.out.println(msg);
    }
}
