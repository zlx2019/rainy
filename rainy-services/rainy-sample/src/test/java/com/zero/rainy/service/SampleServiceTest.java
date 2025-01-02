package com.zero.rainy.service;

import com.zero.rainy.core.model.entity.Sample;
import com.zero.rainy.sample.service.ISampleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * @author Zero.
 * <p> Created on 2025/1/1 21:14 </p>
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SampleServiceTest {
    @Autowired
    private ISampleService sampleService;
    @Autowired
    private ConfigurableEnvironment environment;

    @Test
    public void test() {
        Sample sample = new Sample();
        sample.setName("test");
        sample.setAge(21);
        Assertions.assertTrue(sampleService.save(sample));
        List<Sample> list = sampleService.list();
        list.forEach(System.out::println);
        Assertions.assertEquals(1, list.size());

//        environment.getPropertySources().forEach(props-> {
//            System.out.println(props.getName());
//        });
    }
}
