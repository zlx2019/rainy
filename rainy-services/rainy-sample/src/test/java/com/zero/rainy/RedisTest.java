package com.zero.rainy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Set;
import java.util.stream.IntStream;

/**
 * @author Zero.
 * <p> Created on 2025/1/6 17:07 </p>
 */
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test(){
        ZSetOperations<String, Object> operations = redisTemplate.opsForZSet();
        ZSetOperations.TypedTuple<Object> tuple = ZSetOperations.TypedTuple.of("TaskResult1", (double) System.currentTimeMillis());
        operations.add("sets", Set.of(tuple));
        IntStream.range(1, 11)
                .boxed()
                .forEach(i -> {
                    operations.add("res", "user" + i, i);
                });
    }
}
