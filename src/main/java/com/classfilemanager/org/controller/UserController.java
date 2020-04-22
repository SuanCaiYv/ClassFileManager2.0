package com.classfilemanager.org.controller;

import com.classfilemanager.org.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午5:16
 */
@CrossOrigin(origins = "*")
@RestController
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping(value = "/loginIn")
    public WebAsyncTask<String> loginIn(@RequestParam(name = "id", required = false) String id, @RequestParam(name = "password", required =  false) String password,
                                        HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        return userService.loginIn(id, password, request, response);
    }

    @PostMapping(value = "/updateUser")
    public WebAsyncTask<String> updateUser(@RequestParam(name = "id") String id, @RequestParam(name = "password") String password,
                                           @RequestParam(name = "name") String name, @RequestParam(name = "qq") String qq,
                                           @RequestParam(name = "email") String email)
    {
        return userService.updateUser(id, password, name, qq, email);
    }

    @PostMapping(value = "/sendEmail")
    public WebAsyncTask<String> sendEmail(@RequestParam(name = "id", required = false) String id, HttpServletRequest request, HttpServletResponse response)
    {
        return userService.sendVerificationEmail(id, request, response);
    }

    @PostMapping(value = "/modifyPassword")
    public WebAsyncTask<String> modifyPassword(@RequestParam(name = "id") String id, @RequestParam(name = "password") String password)
    {
        return userService.modifyPassword(id, password);
    }
}
