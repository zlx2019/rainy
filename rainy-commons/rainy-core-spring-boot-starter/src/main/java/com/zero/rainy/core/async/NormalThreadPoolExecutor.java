package com.zero.rainy.core.async;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * 自定义线程池 - 基于#{@link ExecutorService 进行扩展 搭配TransmittableThreadLocal实现父子线程之间的数据传递}
 *
 * @author Zero.
 * <p> Created on 2024/9/2 10:59 </p>
 */
@Slf4j
public class NormalThreadPoolExecutor extends ThreadPoolExecutor {

    /**
     * @param corePoolSize    the number of threads to keep in the pool, even
     * @param maximumPoolSize the maximum number of threads to allow in the
     * @param keepAliveTime   when the number of threads is greater than
     * @param unit            the time unit for the {@code keepAliveTime} argument
     * @param workQueue       the queue to use for holding tasks before they are
     * @param threadFactory   the factory to use when the executor
     * @param handler         the handler to use when execution is blocked
     */
    public NormalThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    @Override
    public void execute(Runnable runnable) {
        super.execute(TtlRunnable.get(runnable));
    }


    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(TtlRunnable.get(task));
    }


    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(TtlRunnable.get(task), result);
    }


    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(TtlCallable.get(task));
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return super.invokeAll(TtlCallable.gets(tasks));
    }
}
