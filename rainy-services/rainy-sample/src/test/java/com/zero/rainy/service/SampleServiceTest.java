package com.zero.rainy.service;

import com.zero.rainy.core.model.entity.Sample;
import com.zero.rainy.sample.service.ISampleService;
import com.zero.rainy.test.AssuredAbstractsApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * @author Zero.
 * <p> Created on 2025/1/1 21:14 </p>
 */

public class SampleServiceTest extends AssuredAbstractsApiTest {
    @Autowired
    private ISampleService sampleService;

    @Test
    public void test() {
        Sample sample = new Sample();
        sample.setName("test");
        sample.setAge(21);
        Assertions.assertTrue(sampleService.save(sample));
        List<Sample> list = sampleService.list();
        list.forEach(System.out::println);
        Assertions.assertEquals(1, list.size());
    }
}
