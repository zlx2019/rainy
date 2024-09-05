package com.zero.rainy.feign;

import com.zero.rainy.feign.config.DefaultFeignConfigure;
import org.springframework.context.annotation.Import;

/**
 * Feign 自动装配器
 *
 * @author Zero.
 * <p> Created on 2024/9/3 15:42 </p>
 */
@Import(DefaultFeignConfigure.class)
public class FeignAutoConfigurer {

}
