package com.zero.rainy.core.spring;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring 上下文扩展工具
 *
 * @author Zero.
 * <p> Created on 2024/8/28 14:03 </p>
 */
@Slf4j
@Component
@SuppressWarnings("all")
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
     * 根据Class类型获取对应的Bean实例
     *
     * @param clazz Bean Class
     * @param <T>   Bean 原型
     * @return      Bean 实例
     */
    public static <T> T getBean(Class<T> clazz) {
        return getBeanFactory().getBean(clazz);
    }

    /**
     * 根据Name 获取Bean
     *
     * @param beanName Bean 名称
     * @param <T>      Bean 原型
     * @return         Bean 实例
     */
    public static <T> T getBean(String beanName) {
        getBeanFactory().getBeanDefinitionCount();
        return (T) getBeanFactory().getBean(beanName);
    }

    /**
     * 根据BeanType及BeanName 获取Bean
     *
     * @param beanName Bean 名称
     * @param clazz    Bean Class
     * @param <T>      Bean 原型
     * @return         Bean 实例
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return getBeanFactory().getBean(beanName, clazz);
    }

    /**
     * 根据Bean Name 获取Bean的原型
     *
     * @param beanName Bean名称
     * @return         Bean原型
     */
    public static Class<?> getBeanType(String beanName) {
        return getBeanFactory().getType(beanName);
    }

    /**
     * IOC是否包含该名称的Bean
     *
     * @param beanName bean名称
     * @return         是/否
     */
    public static boolean containsBean(String beanName) {
        return getBeanFactory().containsBean(beanName);
    }


    /**
     * 获取当前 Bean 的代理对象.
     * @param self  Bean this
     */
    public static <T> T getAopProxy(T self){
        return (T) AopContext.currentProxy();
    }

    /**
     * 根据Key 获取配置属性
     */
    public static String getEnvironmentConfig(String key){
        return applicationContext.getEnvironment().getRequiredProperty(key);
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
