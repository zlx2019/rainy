package com.zero.rainy.feign;

import com.zero.rainy.feign.config.DefaultFeignConfigurer;
import org.springframework.context.annotation.Import;

/**
 * Feign 自动装配类
 *
 * @author Zero.
 * <p> Created on 2024/9/3 15:42 </p>
 */
@Import(DefaultFeignConfigurer.class)
public class FeignAutoConfigurer {

}
