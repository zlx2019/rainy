package com.zero.rainy.core.ext.dynamic;

import com.zero.rainy.core.enums.DynamicConfigKeys;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dynamic config context
 *
 * @author Zero.
 * <p> Created on 2024/11/16 16:11 </p>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DynamicPropertiesContext {
    private static final Map<DynamicConfigKeys, Object> CONFIG_CONTAINER = new ConcurrentHashMap<>(16);

    public static void registryConfig(DynamicConfigKeys key, Object config){
        if (!hasRegistry(key)){
            CONFIG_CONTAINER.put(key, config);
        }
    }
    public static boolean hasRegistry(DynamicConfigKeys key){
        return CONFIG_CONTAINER.containsKey(key);
    }
    public static Set<Object> getConfigs(){
        return new HashSet<>(CONFIG_CONTAINER.values());
    }
    public static <T> T getConfig(DynamicConfigKeys key, Class<T> clazz){
        Object config = CONFIG_CONTAINER.get(key);
        if (clazz.isInstance(config)){
            return clazz.cast(config);
        }
        log.error("Config value {} cannot be case to {}", config, clazz.getName());
        throw new IllegalStateException();
    }

}
