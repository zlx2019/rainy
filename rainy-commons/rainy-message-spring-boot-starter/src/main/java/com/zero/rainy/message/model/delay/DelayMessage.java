package com.zero.rainy.message.model.delay;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zero.rainy.message.model.BaseMessage;
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
    @JsonIgnore
    private Duration delay = Duration.ofSeconds(3);
}
