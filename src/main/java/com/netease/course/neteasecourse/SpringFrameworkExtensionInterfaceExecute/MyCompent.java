package com.netease.course.neteasecourse.SpringFrameworkExtensionInterfaceExecute;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 实现下列接口，就可以获得对应的容器相关的变量
 *
 **/
@Component
public class MyCompent implements ApplicationContextAware,InitializingBean,EnvironmentAware {

    /**
     * 用于bean实例化之后，设置属性的方法
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("==> execute InitializingBean.afterPropertiesSet");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("==> execute ApplicationContextAware.setApplicationContext");
    }

    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("==> execute EnvironmentAware.setEnvironment");
    }
}
