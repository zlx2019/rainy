package com.zero.rainy.sample.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * EMailTemporaryRepository
 *
 * @author Zero.
 * <p> Created on 2024/12/5 10:38 </p>
 */
public interface TemporaryRepository<E> {
    void putAll(E store);
}
