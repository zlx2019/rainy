package com.zero.rainy.core.ext.dynamic;

import com.zero.rainy.core.enums.DynamicConfigKey;
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
    private static final Map<DynamicConfigKey, DynamicProperties> CONFIG_CONTAINER = new ConcurrentHashMap<>(16);

    public static <T extends DynamicProperties> void registryConfig(DynamicConfigKey key, T config){
        if (!hasRegistry(key)){
            CONFIG_CONTAINER.put(key, config);
        }
    }
    public static boolean hasRegistry(DynamicConfigKey key){
        return CONFIG_CONTAINER.containsKey(key);
    }
    public static Set<DynamicProperties> getConfigs(){
        return new HashSet<>(CONFIG_CONTAINER.values());
    }
    public static <T extends DynamicProperties> T getConfig(DynamicConfigKey key, Class<T> clazz){
        DynamicProperties config = CONFIG_CONTAINER.get(key);
        if (clazz.isInstance(config)){
            return clazz.cast(config);
        }
        log.error("Config value {} cannot be case to {}", config, clazz.getName());
        throw new IllegalStateException();
    }

}
