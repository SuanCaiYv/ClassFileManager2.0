package com.classfilemanager.org.result;

import org.springframework.stereotype.Component;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午4:23
 */
@Component
public class ResultBean<T, U>
{

    public static final int SUCCESS = 0;
    public static final int PASSWORD_ERROR = 1;
    public static final int NO_USER = 2;
    /**
     * 状态码
     */
    private int code = SUCCESS;
    /**
     * 相应的信息
     */
    private String msg;
    /**
     * 主数据
     */
    private T data;
    /**
     * 附加数据
     */
    private U addData;

    public U getAddData()
    {
        return addData;
    }

    public void setAddData(U addData)
    {
        this.addData = addData;
    }

    public ResultBean(String msg, T data)
    {
        this.msg = msg;
        this.data = data;
    }

    public ResultBean()
    {
    }

    public ResultBean(T data)
    {
        this.data = data;
    }

    public ResultBean(String msg)
    {
        this.msg = msg;
    }

    public ResultBean(int code, String msg, T data)
    {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }
}
