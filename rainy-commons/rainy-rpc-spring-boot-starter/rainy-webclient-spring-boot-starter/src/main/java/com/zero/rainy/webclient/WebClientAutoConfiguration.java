package com.zero.rainy.webclient;

import com.zero.rainy.webclient.config.WebClientConfigure;
import com.zero.rainy.webclient.factory.ClientProxyFactory;
import org.springframework.context.annotation.Import;

/**
 * Web Client 自动装配器
 *
 * @author Zero.
 * <p> Created on 2024/9/4 14:22 </p>
 */
@Import({WebClientConfigure.class, ClientProxyFactory.class})
public class WebClientAutoConfiguration {

}
