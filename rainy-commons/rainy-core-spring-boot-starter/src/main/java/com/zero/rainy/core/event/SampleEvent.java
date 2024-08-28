package com.zero.rainy.core.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 演示事件对象
 *
 * @author Zero.
 * <p> Created on 2024/8/28 14:44 </p>
 */
@Getter
public class SampleEvent extends ApplicationEvent {
    /**
     * 事件数据
     */
    private final String src;

    public SampleEvent(final Object source, final String src) {
        super(source);
        this.src = src;
    }
}
