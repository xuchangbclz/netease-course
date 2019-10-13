package com.netease.course.neteasecourse.SpringFrameworkExtensionInterfaceExecute;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Spring IOC过程中最后两个扩展接口。
 *  postProcessBeforeInitialization用于在 bean 实例化之后，afterPropertiesSet方法之前执行的前置接口
 *  postProcessAfterInitialization用于bean初始化之后的后置处理。IOC流程执行到此处，一个完整的bean已经创建结束，可在此处对 bean 进行包装或者代理。Spring AOP 原理便是基于此扩展点实现
 **/
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("==> execute BeanPostProcessor.postProcessBeforeInitialization");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("==> execute BeanPostProcessor.postProcessAfterInitialization");
        return bean;
    }
}
