package com.zero.rainy;

import com.zero.rainy.core.ext.dynamic.EnableDynamicConfigurationProperties;
import com.zero.rainy.message.template.MessageTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.convert.ConversionService;

/**
 * 模板服务 启动类.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 13:33 </p>
 */
@Slf4j
@SpringBootApplication
@EnableDynamicConfigurationProperties
//@EnableDiscoveryClient
//@EnableFeignClients
public class RainySampleApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(RainySampleApplication.class, args);
    }
    @Autowired
    private MessageTemplate messageTemplate;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
//        String yaml = Files.readString(Path.of("a.yml"));
//        TestProperties bound = YamlHelper.bind(yaml,"test", TestProperties.class);
//        log.info("{}", bound);

        // JSON to properties
//        String json = Files.readString(Path.of("a.json"));
//        TestProperties unmarshal = JsonUtils.unmarshal(json, TestProperties.class);
//        log.info("{}", unmarshal);
    }
}
