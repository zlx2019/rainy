package com.zero.rainy.core.lock;

/**
 * 分布式信号量
 *
 * @see java.util.concurrent.Semaphore
 * @author Zero.
 * <p> Created on 2024/12/14 00:13 </p>
 */
public interface DistributedSemaphore {

    /**
     * 获取指定数量的许可
     * @param permits   许可数量
     * @return          是否获取成功
     */
    boolean acquire(int permits);

    /**
     * 释放指定数量的许可
     * @param permits   许可数量
     */
    void release(int permits);
}
