package com.zero.rainy;

import com.zero.rainy.cache.template.CacheTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author Zero.
 * <p> Created on 2025/1/22 12:58 </p>
 */
@SpringBootTest(classes = RainyCodeGenApplication.class)
public class RedisTest {

    @Autowired
    private CacheTemplate cacheTemplate;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
//        GenerateDTO dto = new GenerateDTO();
//        dto.setAuthor("zero");
//        dto.setModuleName("user");
//        dto.setPackageName("com.zero.rainy");
//        dto.setTables(Arrays.asList("user", "config", "member"));
//        cacheTemplate.set("xxxobj", dto);
//
//        Object xxxobj = cacheTemplate.get("xxxobj");
//        System.out.println(xxxobj);
        Object value = cacheTemplate.zPopMaxByScore("ez-captcha:task:solution-ready-buffer:2025-01-24:ReCaptcha", Long.valueOf(1737703346833L).doubleValue());
        System.out.println(value);
    }
}
