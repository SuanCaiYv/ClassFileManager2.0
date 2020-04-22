package com.classfilemanager.org.config;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;


/**
 * 配置全局线程池
 * @author SuanCaiYv
 * @time 2020/2/29 下午3:06
 */
@Component
@EnableAsync
public class ThreadExecutor implements Executor
{
    private final ThreadPoolExecutor executor;
    private static final int CORE_POOL_SIZE = 50;
    private static final int MAX_POOL_SIZE = 200;
    private static final int KEEP_ALIVE_TIME = 0;
    private static final int WORK_QUEUE_SIZE = 400;
    private final LinkedBlockingDeque<Runnable> linkedBlockingDeque;
    private final RejectedExecutionHandler handler;

    public ThreadExecutor()
    {
        linkedBlockingDeque = new LinkedBlockingDeque<>();
        handler = (r, executor) -> linkedBlockingDeque.offer(r);
        executor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(WORK_QUEUE_SIZE), handler);
    }

    final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    /**
     * 检查可执行队列是否有空余位置, 有的话把linkedBlockingDeque里面的任务放进去
     */
    final ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new Runnable()
    {
        @Override
        public void run()
        {
            if (!linkedBlockingDeque.isEmpty()) {
                executor.execute((Runnable) linkedBlockingDeque.poll());
            }
        }
    }, 0, 500, TimeUnit.MILLISECONDS);

    @Override
    public void execute(Runnable runnable)
    {
        executor.execute(runnable);
    }

    public <T> Future<T> submit(Callable<T> callable)
    {
        return executor.submit(callable);
    }

    public Future<?> submit(Runnable runnable)
    {
        return executor.submit(runnable);
    }

    public Future<?> schedule(Runnable runnable)
    {
        return scheduledExecutorService.schedule(runnable, 15, TimeUnit.MINUTES);
    }

    public void destroy()
    {
        scheduledExecutorService.shutdown();
        executor.shutdown();
    }

}
