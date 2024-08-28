package com.zero.rainy.core.listener;

import com.zero.rainy.core.event.EmptyEvent;
import com.zero.rainy.core.event.SampleEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * {@link SampleEvent} 事件监听器
 *
 * @author Zero.
 * <p> Created on 2024/8/28 14:47 </p>
 */
@Component
@Slf4j
public class SampleEventListener implements ApplicationListener<SampleEvent> {

    @Override
    public void onApplicationEvent(SampleEvent event) {
        log.info("on SampleEvent: {}", event.getSrc());
    }

    @EventListener(EmptyEvent.class)
    public void emptyEventListener(EmptyEvent event){
        log.info("on EmptyEvent...");
    }
}
