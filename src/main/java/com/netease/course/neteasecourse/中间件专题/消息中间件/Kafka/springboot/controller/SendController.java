package com.netease.course.neteasecourse.中间件专题.消息中间件.Kafka.springboot.controller;

import com.netease.course.neteasecourse.中间件专题.消息中间件.Kafka.springboot.producer.Message;
import com.netease.course.neteasecourse.中间件专题.消息中间件.Kafka.springboot.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: allen
 * @Date: 2019/2/26 15:03
 */
@RestController
@RequestMapping("kafka")
public class SendController {

    @Autowired
    private Producer producer;

    @RequestMapping(value = "send")
    public String send() {
        producer.sendMessage(new Message());
        return "{\"code\":0}";
    }
}
