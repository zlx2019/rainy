package com.zero.rainy.core.ext.dynamic;

import com.zero.rainy.core.enums.DynamicPropertiesKey;
import com.zero.rainy.core.ext.container.CopyOnWriteMap;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Dynamic config context
 *
 * @author Zero.
 * <p> Created on 2024/11/16 16:11 </p>
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DynamicPropertiesContext {
    private static final Map<DynamicPropertiesKey, DynamicProperties> CONFIG_CONTAINER = new CopyOnWriteMap<>(new HashMap<>(32));

    public static <T extends DynamicProperties> void registryConfig(DynamicPropertiesKey key, T config){
        if (!hasRegistry(key)){
            CONFIG_CONTAINER.put(key, config);
        }
    }
    public static boolean hasRegistry(DynamicPropertiesKey key){
        return CONFIG_CONTAINER.containsKey(key);
    }
    public static Map<DynamicPropertiesKey, DynamicProperties> getConfigs(){
        return CONFIG_CONTAINER;
    }
    public static <T extends DynamicProperties> T getConfig(DynamicPropertiesKey key, Class<T> clazz){
        DynamicProperties config = CONFIG_CONTAINER.get(key);
        if (clazz.isInstance(config)){
            return clazz.cast(config);
        }
        log.error("Config value {} cannot be case to {}", config, clazz.getName());
        throw new IllegalStateException();
    }

}
