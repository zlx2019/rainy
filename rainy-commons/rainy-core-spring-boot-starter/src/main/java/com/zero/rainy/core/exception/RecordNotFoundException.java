package com.zero.rainy.core.exception;

import lombok.Getter;

/**
 * 通过ID或唯一索引查询记录时不存在，导致无法正常执行业务.
 *
 * @author Zero.
 * <p> Created on 2025/1/1 22:02 </p>
 */
@Getter
public class RecordNotFoundException extends BusinessException {
    private final String key;
    public RecordNotFoundException(String key) {
        this(key, "Business data not found: " + key);
    }
    public RecordNotFoundException(String key, String message) {
        super(message);
        this.key = key;
    }
}
