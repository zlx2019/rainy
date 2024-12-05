package com.zero.rainy.sample.service;

import com.zero.rainy.sample.model.MailMessage;
import com.zero.rainy.sample.model.MailStorage;
import com.zero.rainy.sample.model.MailStorageEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Zero.
 * <p> Created on 2024/12/5 10:54 </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailTemporaryRepository implements TemporaryRepository<MailStorage<MailStorageEntity>> {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void putAll(MailStorage<MailStorageEntity> store) {
        String key = store.getKey();
        MailStorageEntity elems = store.getElems();
        HashOperations<String, Object, Object> opt = redisTemplate.opsForHash();
//        for (Map.Entry<String, MailMessage> entry : elems.entrySet()) {
//            opt.put(key, entry.getKey(), entry.getValue());
//        }
        opt.putAll(key, elems);
    }
}
