package com.zero.rainy.limiter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 限流模式
 * @author Zero.
 * <p> Created on 2025/2/28 15:12 </p>
 */
@Getter
@AllArgsConstructor
public enum LimiterMode {
    STANDALONE,
    CLUSTER;
}
