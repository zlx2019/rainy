package com.zero.rainy.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 空事件对象
 * @author Zero.
 * <p> Created on 2024/8/28 15:15 </p>
 */
public class EmptyEvent extends ApplicationEvent {

    public EmptyEvent(Object source) {
        super(source);
    }
}
