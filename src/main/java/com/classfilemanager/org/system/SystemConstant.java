package com.classfilemanager.org.system;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author SuanCaiYv
 * @time 2020/2/23 下午9:33
 */
public class SystemConstant implements InitializingBean
{
    /**
     * 文件保存基础路径
     */
    @Value("${basePath}")
    private String basePath;
    /**
     *间隔: (天)
     */
    @Value("${interval}")
    private int interval;

    public static String BASE_PATH;
    public static int INTERVAL;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        BASE_PATH = basePath;
        INTERVAL = interval;
    }
}
