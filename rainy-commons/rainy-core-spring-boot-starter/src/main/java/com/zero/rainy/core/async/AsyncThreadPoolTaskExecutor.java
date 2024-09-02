package com.zero.rainy.core.async;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * 重写Spring 默认异步线程池，搭配 {@link TransmittableThreadLocal} 实现父子线程之间的数据传递
 *
 * @author Zero.
 * <p> Created on 2024/9/2 10:59 </p>
 */
public class AsyncThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable task) {
        super.execute(TtlRunnable.get(task));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(TtlCallable.get(task));
    }

    @Override
    public Future<?> submit(Runnable task) {
        Runnable ttlRunnable = TtlRunnable.get(task);
        return super.submit(ttlRunnable);
    }

    @Override
    public CompletableFuture<Void> submitCompletable(Runnable task) {
        return super.submitCompletable(TtlRunnable.get(task));
    }

    @Override
    public <T> CompletableFuture<T> submitCompletable(Callable<T> task) {
        return super.submitCompletable(TtlCallable.get(task));
    }


}