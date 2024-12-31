package com.zero.rainy;

import com.zero.rainy.core.model.entity.Sample;
import com.zero.rainy.sample.model.converts.SampleConvert;
import com.zero.rainy.sample.model.dto.SampleDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Zero.
 * <p> Created on 2025/1/1 01:45 </p>
 */
@SpringBootTest
public class SampleConvertTest {
    @Autowired
    private SampleConvert sampleConvert;

    @Test
    public void test(){
        SampleDTO dto = new SampleDTO();
        dto.setId(1111L);
        dto.setAge(19);
        dto.setName("zhangsan");
        Sample entity = SampleConvert.INSTANCE.toEntity(dto);
        System.out.println(entity);
        System.out.println(entity.getId());
    }
}
