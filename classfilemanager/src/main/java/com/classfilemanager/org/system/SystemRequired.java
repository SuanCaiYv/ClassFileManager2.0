package com.classfilemanager.org.system;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author SuanCaiYv
 * @time 2020/2/23 下午9:34
 */
@Component
@EnableScheduling
public class SystemRequired
{
    /**
     * 任务的保留时间
     */
    private int interval = SystemConstant.INTERVAL*24*60*60*1000;

    /**
     * 执行定时任务
     */
    @Scheduled(fixedRate = 24*60*60*1000)
    public void f()
    {
        ;
    }
}
