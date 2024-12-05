package com.zero.rainy.sample.model;

import java.util.Map;

/**
 * 临时存储库
 *
 * @author Zero.
 * <p> Created on 2024/12/5 13:26 </p>
 */
public interface Storage<V> extends Map<String, V> {
    /**
     * Hash Key
     */
    String getKey();

    /**
     * Hash K-V
     */
    Map<String, V> getStore();
}
