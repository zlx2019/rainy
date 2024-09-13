package com.zero.rainy.webclient;

import com.zero.rainy.webclient.config.DefaultWebClientConfigure;
import com.zero.rainy.webclient.factory.ClientProxyFactory;
import org.springframework.context.annotation.Import;

/**
 * Web Client 自动装配器
 *
 * @author Zero.
 * <p> Created on 2024/9/4 14:22 </p>
 */
@Import({DefaultWebClientConfigure.class, ClientProxyFactory.class})
public class WebClientAutoConfigurer {

}
