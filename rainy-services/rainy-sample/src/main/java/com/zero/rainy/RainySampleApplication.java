package com.zero.rainy;

import com.zero.rainy.core.event.EmptyEvent;
import com.zero.rainy.core.event.SampleEvent;
import com.zero.rainy.core.spring.SpringContextUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 模板服务 启动类.
 *
 * @author Zero.
 * <p> Created on 2024/8/27 13:33 </p>
 */
@SpringBootApplication
public class RainySampleApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(RainySampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        SpringContextUtil.publishEvent("哈哈哈", SampleEvent.class);
        SpringContextUtil.publishEvent(EmptyEvent.class);
    }
}
