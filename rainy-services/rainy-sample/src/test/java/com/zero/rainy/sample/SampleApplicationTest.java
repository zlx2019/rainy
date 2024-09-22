package com.zero.rainy.sample;

import com.zero.rainy.core.entity.Sample;
import com.zero.rainy.sample.service.ISampleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Zero.
 * <p> Created on 2024/9/3 16:36 </p>
 */
@SpringBootTest
public class SampleApplicationTest {
    @Autowired
    private ISampleService sampleService;

    @Test
    public void list(){
        sampleService.list().forEach(System.out::println);
    }

    @Test
    public void insert(){
        Sample sample = new Sample();
        sample.setName("Zero");
        sample.setAge(18);
        Assertions.assertTrue(sampleService.save(sample));
    }

    @Test
    public void update(){
        Sample sample = sampleService.getById(1837364931811069952L);
        sample.setAge(30);
        Assertions.assertTrue(sampleService.updateById(sample));
    }

    @Test
    public void delete(){
        Sample sample = new Sample();
        sample.setId(1837364931811069952L);
        Assertions.assertTrue(sampleService.removeById(sample));
    }

    @Test
    public void lock(){
        Sample sample = sampleService.getById(1837364931811069952L);
        sample.setAge(18);
        Assertions.assertTrue(sampleService.lockUpdate(sample));
    }
}
