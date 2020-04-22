package com.classfilemanager.org.exception;

/**
 * @author SuanCaiYv
 * @time 2020/2/23 下午8:58
 */
public class UnhandledException extends Exception
{
    private String msg;
    private String location;

    public UnhandledException(String msg, String location)
    {
        super(msg);
        this.msg = msg;
        this.location = location;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getMsg()
    {
        return msg;
    }
}
