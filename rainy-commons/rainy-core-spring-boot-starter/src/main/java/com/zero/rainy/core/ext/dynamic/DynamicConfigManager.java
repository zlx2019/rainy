package com.zero.rainy.core.ext.dynamic;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Zero.
 * <p> Created on 2024/11/6 22:49 </p>
 */
@Component
@DependsOn("dataSource")
@RequiredArgsConstructor
public class DynamicConfigManager implements InitializingBean {
    private final DataSource dataSource;
    private final Map<String, String> MAPPING = new ConcurrentHashMap<>(32);

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DynamicConfigManager - init");
    }
}
