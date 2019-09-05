package com.netease.course.neteasecourse.高性能编程专题.Java性能调优.Tomcat参数调优;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
        configurer.setDefaultTimeout(60 * 1000L);
        configurer.registerCallableInterceptors(timeoutInterceptor());
        configurer.setTaskExecutor(threadPoolTaskExecutor());
    }

    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }

    /**
     * ThreadPoolTaskExecutor是一个spring的线程池技术,@Async 异步注解的线程就是基于该线程池
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor t = new ThreadPoolTaskExecutor();
        t.setCorePoolSize(200);
        t.setMaxPoolSize(200);
        t.setThreadNamePrefix("netease-demo-");
        return t;
    }

}
