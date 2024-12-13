package com.zero.rainy;

import com.zero.rainy.cache.lock.RedisDistributedLock;
import com.zero.rainy.core.lock.DistributedLock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author Zero.
 * <p> Created on 2024/12/14 00:03 </p>
 */
@SpringBootTest
public class RedisDistributedLockTest {
    @Autowired
    private DistributedLock distributedLock;

    @Test
    public void testLock() throws InterruptedException {
        boolean locked = distributedLock.lock("lock1", Duration.ofSeconds(30));
        Assertions.assertTrue(locked);
        Assertions.assertFalse(distributedLock.lock("lock1"));
        TimeUnit.SECONDS.sleep(5);
        Assertions.assertTrue(distributedLock.unlock("lock1"));
    }
}
