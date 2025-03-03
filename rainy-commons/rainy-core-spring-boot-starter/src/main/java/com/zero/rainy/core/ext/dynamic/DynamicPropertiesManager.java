package com.zero.rainy.core.ext.dynamic;

import com.zero.rainy.core.enums.supers.Status;
import com.zero.rainy.core.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态配置管理器
 * @author Zero.
 * <p> Created on 2024/11/6 22:49 </p>
 */
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings({"all"})
public class DynamicPropertiesManager<T extends DynamicProperties> implements InitializingBean, BeanPostProcessor {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Map<String, String> CONFIG_CONTENTS = new ConcurrentHashMap<>(32);
    private final String CONFIG_KEY = "config_key";
    private final String CONFIG_VALUE = "config_value";


    /**
     * 当前Bean初始化后，加载所有配置
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("[DynamicPropertiesManager] - init");
        loadAllDynamicConfigs();
    }

    /**
     * Bean 初始化前置处理
     * 将所有配置对象(实现了{@link DynamicProperties}接口的实体) 注册到上下文容器 {@link DynamicPropertiesContext#CONFIG_CONTAINER}
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DynamicProperties propsBean) {
            DynamicPropertiesMark annotation = propsBean.getClass().getAnnotation(DynamicPropertiesMark.class);
            if (annotation != null) {
                DynamicPropertiesContext.registryConfig(annotation.value(), propsBean);
            }
        }
        return bean;
    }

    /**
     * Bean 初始化后置处理
     * 将配置内容序列化注入到实体对象
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DynamicProperties propsBean) {
            DynamicPropertiesMark annotation = propsBean.getClass().getAnnotation(DynamicPropertiesMark.class);
            if (annotation != null) {
                // TODO 处理多配置类型
                String configValue = this.getConfigValue(annotation.value().getKey());
                if (Objects.nonNull(configValue)) {
                    DynamicProperties config = JsonUtils.unmarshal(configValue, propsBean.getClass());
                    BeanUtils.copyProperties(config, propsBean);
                }
            }
        }
        return bean;
    }

    /**
     * 根据配置标识，获取配置内容
     *
     * @param key 配置唯一标识
     */
    public String getConfigValue(String key) {
        return CONFIG_CONTENTS.get(key);
    }

    /**
     * 从数据库加载所有配置
     */
    public void loadAllDynamicConfigs(){
        MapSqlParameterSource param = new MapSqlParameterSource("status", Status.NORMAL.getCode());
        String SQL = "SELECT config_key, config_value FROM config WHERE status = :status";
        jdbcTemplate.query(SQL, param, (rs) -> {
            CONFIG_CONTENTS.put(rs.getString(CONFIG_KEY), rs.getString(CONFIG_VALUE));
        });
        log.info("[DynamicPropertiesManager] reload all dynamic configs");
    }
}
