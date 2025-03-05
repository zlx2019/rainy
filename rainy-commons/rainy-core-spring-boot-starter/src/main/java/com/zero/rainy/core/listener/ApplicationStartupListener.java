package com.zero.rainy.core.listener;

import com.zero.rainy.core.ext.dynamic.DynamicPropertiesContext;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author Zero.
 * <p> Created on 2025/3/5 16:13 </p>
 */
@Component
@SuppressWarnings("all")
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        DynamicPropertiesContext.prints();
    }
}
