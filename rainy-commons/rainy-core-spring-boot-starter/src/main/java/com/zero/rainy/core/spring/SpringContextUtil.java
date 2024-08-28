package com.zero.rainy.core.spring;

import com.zero.rainy.core.utils.AssertUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Constructor;

/**
 * Spring 上下文扩展工具
 *
 * @author Zero.
 * <p> Created on 2024/8/28 14:03 </p>
 */
@Component
@Slf4j
public class SpringContextUtil implements ApplicationContextAware, BeanFactoryPostProcessor {
    @Getter
    private static ApplicationContext applicationContext;
    private static ConfigurableListableBeanFactory beanFactory;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringContextUtil.beanFactory = beanFactory;
    }
    public static ListableBeanFactory getBeanFactory() {
        ListableBeanFactory factory = beanFactory == null ? applicationContext : beanFactory;
        if (factory == null) {
            throw new RuntimeException("No ConfigurableListableBeanFactory or ApplicationContext injected, maybe not in the Spring environment?");
        }
        return factory;
    }
    public static ConfigurableListableBeanFactory getConfigurableBeanFactory(){
        if(beanFactory != null){
            return beanFactory;
        }else{
            if (applicationContext instanceof ConfigurableApplicationContext context){
                return context.getBeanFactory();
            }
            throw new RuntimeException("ConfigurableApplicationContext does not exist in the current environment");
        }
    }

    /**
     * 发布事件
     * @param src   事件源
     * @param event 事件对象
     */
    public static<R, T extends ApplicationEvent> void publishEvent(R src, Class<T> event){
        T instance = buildEventInstance(src, event);
        if (null != instance){
            applicationContext.publishEvent(instance);
        }
    }

    /**
     * 发布空事件
     * @param event 事件对象
     */
    public static <T extends ApplicationEvent> void publishEvent(Class<T> event){
        publishEvent(null, event);
    }

    private static <R, T extends ApplicationEvent> T buildEventInstance(R src, Class<T> event){
        try {
            if (src == null){
                return event.getConstructor(Object.class).newInstance(applicationContext);
            }else {
                return event.getConstructor(Object.class, src.getClass()).newInstance(applicationContext, src);
            }
        }catch (Exception e){
            if (e instanceof NoSuchMethodException){
                log.error("Event [{}] no matching constructor: [Object, {}]", event.getTypeName(), src.getClass().getSimpleName(), e);
            }else {
                log.error("event instance build error", e);
            }
            return null;
        }
    }
}
