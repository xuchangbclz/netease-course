package com.netease.course.neteasecourse.中间件专题.缓存中间件.Redis.数据存储类型与常用命令;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * redis功能：数据库，缓存中间件，消息队列，Lock...
 *
 * 通用命令：
 *  del key
 *  exists key
 *  expire key seconds
 *  ttl key
 *  type key
 *  keys *
 *  set key value
 *  get key
 *
 **/
public class RedisCommand {

    // 直接注入StringRedisTemplate，则代表每一个操作参数都是字符串
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 参数可以是任何对象，默认由JDK序列化
    @Autowired
    private RedisTemplate redisTemplate;

}
