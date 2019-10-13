package com.netease.course.neteasecourse.SpringFrameworkExtensionInterfaceExecute;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * 使用场景：一般用来在读取所有的BeanDefinition信息之后，实例化之前，通过该接口可进一步自行处理，比如修改beanDefinition等
 **/
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("==> execute BeanFactoryPostProcessor.postProcessBeanFactory");
    }
}
