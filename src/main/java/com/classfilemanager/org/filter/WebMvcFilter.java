package com.classfilemanager.org.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 此Filter主要处理XMLHttpRequest跨域提交Cookie的问题
 * @author SuanCaiYv
 * @time 2020/3/3 上午12:09
 */
@Component
@WebFilter(value = "/*", filterName = "webMvcFilter")
public class WebMvcFilter implements Filter
{
    @Override
    public void doFilter(ServletRequest request_, ServletResponse response_, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) request_;
        HttpServletResponse response = (HttpServletResponse) response_;
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, userId, token");
        response.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, PATCH, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "6400");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        //此行代码确保请求可以继续执行至Controller
        chain.doFilter(request, response);
    }
}
