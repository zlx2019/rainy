package com.zero.rainy.core.ext.dynamic;

import com.zero.rainy.core.enums.supers.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态配置管理器
 * @author Zero.
 * <p> Created on 2024/11/6 22:49 </p>
 */
@Component
@RequiredArgsConstructor
public class DynamicConfigManager implements InitializingBean {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Map<String, String> CONFIG_TABLES = new ConcurrentHashMap<>(16);
    private final String SQL = "SELECT config_key, config_value FROM config WHERE status = :status";
    private final String CONFIG_KEY = "config_key";
    private final String CONFIG_VALUE = "config_value";

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DynamicConfigManager - init");
        // 初始化时从数据库加载配置
        loadAllDynamicConfigs();
    }

    public String getConfigValue(String key) {
        return CONFIG_TABLES.get(key);
    }

    public void loadAllDynamicConfigs(){
        MapSqlParameterSource param = new MapSqlParameterSource("status", Status.NORMAL.getCode());
        jdbcTemplate.query(SQL, param, (rs) -> {
            CONFIG_TABLES.put(rs.getString(CONFIG_KEY), rs.getString(CONFIG_VALUE));
        });
    }
}
