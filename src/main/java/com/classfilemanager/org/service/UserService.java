package com.classfilemanager.org.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author SuanCaiYv
 * @time 2020/2/23 下午9:56
 */
@Service
public interface UserService
{
    /**
     * 登录判定
     * @param id 登录者ID
     * @param password 登录者密码
     * @return NA
     * @throws IOException NA
     */
    WebAsyncTask<String> loginIn(String id, String password, HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 更新当前者信息
     * @param id 当前者ID
     * @param password 新的密码
     * @param name 新的名字
     * @param qq 新的QQ
     * @param email 新的邮箱
     * @return NA
     */
    WebAsyncTask<String> updateUser(String id, String password, String name, String qq, String email);

    /**
     * 发送验证邮件并返回验证码
     * @param id NA
     * @return 验证码
     */
    WebAsyncTask<String> sendVerificationEmail(String id, HttpServletRequest request, HttpServletResponse response);

    /**
     * 修改密码
     * @param id 当前者的ID
     * @param password 新密码
     * @return NA
     */
    WebAsyncTask<String> modifyPassword(String id, String password);
}
