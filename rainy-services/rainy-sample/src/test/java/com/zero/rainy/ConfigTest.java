package com.zero.rainy;

import com.zero.rainy.core.enums.ConfigType;
import com.zero.rainy.core.enums.DynamicPropertiesKey;
import com.zero.rainy.core.model.entity.Config;
import com.zero.rainy.sample.service.IConfigService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author Zero.
 * <p> Created on 2025/2/27 23:28 </p>
 */
@SpringBootTest
public class ConfigTest {
    @Autowired
    private IConfigService configService;

    @Test
    public void test() {
        Config config = new Config();
        config.setConfigKey(DynamicPropertiesKey.AUTH);
        config.setConfigValue("""
jwt:
  secret-key: wadhnuikawhduiwahduiawhduiwaduiwauiadw
  ttl: 12h
ignore-urls:
  - /auth/**
                """);
        config.setConfigType(ConfigType.YAML);
        Assertions.assertTrue(configService.save(config));
    }

    @Test
    public void test2() {
        List<Config> list = configService.list();
    }
}
