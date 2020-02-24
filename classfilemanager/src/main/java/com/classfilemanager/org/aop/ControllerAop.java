package com.classfilemanager.org.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author SuanCaiYv
 * @time 2020/2/22 下午5:19
 */
@Aspect
public class ControllerAop
{
    private static Logger logger = LoggerFactory.getLogger(ControllerAop.class);
    private static Date date = new Date();
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Pointcut("execution(* com.classfilemanager.org..*.*(..))")
    public void cut() {}
    @Around("cut()")
    public Object handler(ProceedingJoinPoint joinPoint)
    {
        long currentTime = System.currentTimeMillis();
        date.setTime(currentTime);
        String time = format.format(date);
        try {
            logger.info(time+" 程序执行到: "+joinPoint.getSignature().toString());
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error(time+"  捕获异常: 为"+throwable.toString());
        }
        return null;
    }
}
