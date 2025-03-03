//package com.zero.rainy.core.ext.dynamic;
//
//import com.zero.rainy.core.utils.JsonUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//
//import java.util.Objects;
//
///**
// * @author Zero.
// * <p> Created on 2024/11/6 22:50 </p>
// */
//@RequiredArgsConstructor
//public class DynamicConfigProcessor implements BeanPostProcessor {
//    private final DynamicPropertiesManager dynamicConfigManager;
//
//
//    /**
//     * Bean 初始化前置处理.
//     * 将所有的 DynamicConfig 实例注册到 {@link DynamicConfigContext#CONFIG_CONTAINER}
//     */
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        if (bean instanceof DynamicProperties dynamicConfigBean) {
//            DynamicPropertiesMark annotation = dynamicConfigBean.getClass().getAnnotation(DynamicPropertiesMark.class);
//            if (annotation != null) {
//                // 将配置加载到上下文容器
//                String key = annotation.value().getKey();
//                DynamicConfigContext.registryConfig(key, dynamicConfigBean);
//            }
//        }
//        return bean;
//    }
//
//    /**
//     * Bean 初始化后置处理.
//     * 为 DynamicConfig 配置赋予属性值
//     */
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if (bean instanceof DynamicProperties dynamicConfigBean) {
//            DynamicPropertiesMark annotation = dynamicConfigBean.getClass().getAnnotation(DynamicPropertiesMark.class);
//            if (annotation != null) {
//                String key = annotation.value().getKey();
//                String configValue = dynamicConfigManager.getConfigValue(key);
//                if (Objects.nonNull(configValue)) {
//                    DynamicProperties config = JsonUtils.unmarshal(configValue, dynamicConfigBean.getClass());
//                    BeanUtils.copyProperties(config, dynamicConfigBean);
//                }
//            }
//        }
//        return bean;
//    }
//}
