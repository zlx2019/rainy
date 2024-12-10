package com.zero.rainy.core.model.message.supers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

/**
 * 支持延迟的消息.
 *
 * @author Zero.
 * <p> Created on 2024/9/28 13:46 </p>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class DelayMessage extends BaseMessage {
    /**
     * 延迟时间, 默认为 3 秒
     */
    private Duration delay = Duration.ofSeconds(3);
}
