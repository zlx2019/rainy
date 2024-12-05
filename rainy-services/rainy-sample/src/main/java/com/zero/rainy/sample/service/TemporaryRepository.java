package com.zero.rainy.sample.service;

import java.time.Duration;

/**
 * EMailTemporaryRepository
 *
 * @author Zero.
 * <p> Created on 2024/12/5 10:38 </p>
 */
public interface TemporaryRepository<K, F, V> {
//    void putAll(T store);
//    void putAll(String key, T store);


//    Map<String, V> getStore(String key);

    /**
     * 存储库的有效时长
     */
    Duration expired();
}
