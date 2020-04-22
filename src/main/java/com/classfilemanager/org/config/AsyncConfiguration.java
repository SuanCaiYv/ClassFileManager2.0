package com.classfilemanager.org.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author SuanCaiYv
 * @time 2020/2/29 下午5:40
 */
@Configuration
public class AsyncConfiguration implements AsyncConfigurer
{

    /**
     * 专为WebAsyncTask配置线程池
     * @return NA
     */
    @Bean
    protected WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
                configurer.setTaskExecutor(asyncTaskExecutor());
            }

        };
    }

    @Autowired
    private ThreadExecutor executor;

    private AsyncTaskExecutor asyncTaskExecutor()
    {
        return new AsyncTaskExecutor()
        {
            @Override
            public void execute(Runnable task, long startTimeout)
            {
                executor.execute(task);
            }

            @Override
            public Future<?> submit(Runnable task)
            {
                return executor.submit(task);
            }

            @Override
            public <T> Future<T> submit(Callable<T> task)
            {
                return executor.submit(task);
            }

            @Override
            public void execute(Runnable task)
            {
                executor.execute(task);
            }
        };
    }
}
