package com.zero.rainy;

import com.zero.rainy.message.template.MessageTemplate;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Zero.
 * <p> Created on 2025/2/27 14:00 </p>
 */
@SpringBootTest
public class RocketMQTest {
    @Autowired
    private MessageTemplate messageTemplate;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void testSend() {
        for (int i = 1; i <= 20; i++) {
            messageTemplate.send("mul-consumer-test", "hello" + i);
        }
    }
}
