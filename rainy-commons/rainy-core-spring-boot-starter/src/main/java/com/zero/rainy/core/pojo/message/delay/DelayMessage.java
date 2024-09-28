package com.zero.rainy.core.pojo.message.delay;

import com.zero.rainy.core.pojo.message.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

/**
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
