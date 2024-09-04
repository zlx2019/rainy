package com.zero.rainy.sample;

import com.zero.rainy.api.feign.SampleService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Zero.
 * <p> Created on 2024/9/3 16:36 </p>
 */
@SpringBootTest
@RequiredArgsConstructor
public class SampleApplicationTest {
    private SampleService sampleService;


    @Test
    public void test(){
//        String ping = sampleServiceClient.ping();
//        System.out.println(ping);
    }
}
