package com.zero.rainy.core.ext.dynamic;

import com.zero.rainy.core.enums.ConfigType;
import com.zero.rainy.core.enums.supers.Status;
import com.zero.rainy.core.helper.YamlHelper;
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
    private final Map<String, DynamicPropertiesBo> CONFIG_CACHES = new ConcurrentHashMap<>(32);
    private final String ID = "id";
    private final String CONFIG_KEY = "config_key";
    private final String CONFIG_VALUE = "config_value";
    private final String CONFIG_TYPE = "config_type";
    private final String STATUS = "status";


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
            DynamicPropertiesKeys annotation = propsBean.getClass().getAnnotation(DynamicPropertiesKeys.class);
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
            DynamicPropertiesKeys annotation = propsBean.getClass().getAnnotation(DynamicPropertiesKeys.class);
            if (annotation != null) {
                // TODO 处理多配置类型
                DynamicPropertiesBo configBo = this.getConfigValue(annotation.value().getKey());
                if (Objects.nonNull(configBo)) {
                    String configValue = configBo.configValue();
                    DynamicProperties payload = null;
                    try {
                        switch (configBo.configType()) {
                            case JSON -> payload = JsonUtils.unmarshal(configValue, propsBean.getClass());
                            case YAML -> payload = YamlHelper.bind(configValue, propsBean.getClass());
                        }
                        BeanUtils.copyProperties(payload, propsBean);
                    }catch (Exception e) {
                        log.error("[DynamicPropertiesManager] 动态注入配置属性异常: {}", e.getMessage());
                    }

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
    public DynamicPropertiesBo getConfigValue(String key) {
        return CONFIG_CACHES.get(key);
    }

    /**
     * 从数据库加载所有配置
     */
    public void loadAllDynamicConfigs(){
        MapSqlParameterSource param = new MapSqlParameterSource("status", Status.NORMAL.getCode());
        String SQL = "SELECT id, config_key, config_value, config_type, status FROM config WHERE status = :status AND deleted = 0";
        jdbcTemplate.query(SQL, param, (rs) -> {
            long id = rs.getLong(ID);
            String configKey = rs.getString(CONFIG_KEY);
            String configValue = rs.getString(CONFIG_VALUE);
            String configType = rs.getString(CONFIG_TYPE);
            int status = rs.getInt(STATUS);
            DynamicPropertiesBo bo = new DynamicPropertiesBo(id, configKey, configValue, ConfigType.from(configType), status);
            CONFIG_CACHES.put(configKey, bo);
        });
        log.info("[DynamicPropertiesManager] reload all dynamic configs");
    }
}
