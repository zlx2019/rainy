package com.zero.rainy;

import com.zero.rainy.core.model.entity.Sample;
import com.zero.rainy.sample.mapper.SampleMapper;
import com.zero.rainy.sample.service.ISampleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;


/**
 * @author Zero.
 * <p> Created on 2024/12/1 12:28 </p>
 */
@SpringBootTest
public class InsertBatchTest {
    @Autowired
    private SampleMapper sampleMapper;
    @Autowired
    private ISampleService sampleService;

    @Test
    public void testInsertBatch() {
        List<Sample> list = IntStream.range(0, 2)
                .boxed().map(i -> {
                    Sample sample = new Sample();
                    sample.setName("name" + i);
                    sample.setAge(i);
                    return sample;
                }).toList();
        boolean batched = sampleService.batchSave(list);
        Assertions.assertTrue(batched);
//        int row = sampleMapper.batchInsert(list);
//        System.out.println(row);
//        Assertions.assertEquals(row, 2);
    }

    @Test
    public void testInsertBatch2() {
        List<Sample> list = sampleService.list();
    }
}
