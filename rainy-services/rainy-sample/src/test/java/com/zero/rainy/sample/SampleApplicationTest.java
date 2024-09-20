package com.zero.rainy.sample;

import com.zero.rainy.core.entity.Sample;
import com.zero.rainy.sample.service.ISampleService;
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
    public void test(){
        Sample sample = new Sample();
        sample.setName("lisi");
        sample.setAge(18);
        boolean saved = sampleService.save(sample);
        System.out.println(saved);
    }

    @Test
    public void update(){
        Sample sample = sampleService.getById(1836972328246734848L);
        sample.setAge(22);
        boolean updated = sampleService.updateById(sample);
        System.out.println(updated);
    }

    @Test
    public void delete(){
        Sample sample = new Sample();
        sample.setId(1836972328246734848L);
        boolean updated = sampleService.removeById(sample);
        System.out.println(updated);
    }

    @Test
    public void lock(){
        Sample sample;
        do {
            sample = sampleService.getById(1836967643460829184L);
            sample.setAge(99);
        }while (!sampleService.updateById(sample));
        System.out.println("修改成功!");
    }
}
