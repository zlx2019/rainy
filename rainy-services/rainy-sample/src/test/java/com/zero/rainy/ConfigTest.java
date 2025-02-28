package com.zero.rainy;

import com.zero.rainy.core.enums.ConfigType;
import com.zero.rainy.core.model.entity.Config;
import com.zero.rainy.sample.service.IConfigService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        config.setConfigKey("global");
        config.setConfigValue("{}");
        config.setConfigType(ConfigType.YAML);
        Assertions.assertTrue(configService.save(config));
    }
}
