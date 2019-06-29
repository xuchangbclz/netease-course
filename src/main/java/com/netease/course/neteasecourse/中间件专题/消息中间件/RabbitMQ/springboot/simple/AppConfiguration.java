package com.netease.course.neteasecourse.中间件专题.消息中间件.RabbitMQ.springboot.simple;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Bean
    public Queue helloSpring() {
        return new Queue("spring.cluster");
    }
}
