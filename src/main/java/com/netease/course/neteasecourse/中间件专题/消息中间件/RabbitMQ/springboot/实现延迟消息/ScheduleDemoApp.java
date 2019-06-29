package com.netease.course.neteasecourse.中间件专题.消息中间件.RabbitMQ.springboot.实现延迟消息;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@EnableScheduling
public class ScheduleDemoApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(ScheduleDemoApp.class).run(args);
    }

    @RabbitListener(queues = "delay_queue_3") // 监听死信队列
    public void doSth(String msg) {
        // 你自己的情况 你自己管理 -- ack
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + " --- 收到消息" + msg);
        // 执行异常 --- MQ队列
    }
}
