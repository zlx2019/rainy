package com.zero.rainy;

import com.zero.rainy.message.template.MessageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 模板服务 启动类.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 13:33 </p>
 */
//@EnableDynamicConfig

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
public class RainySampleApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(RainySampleApplication.class, args);
    }
    @Autowired
    private MessageTemplate messageTemplate;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 20; i++) {
            messageTemplate.send("mul-consumer-test", "hello" + i);
        }
    }
}
