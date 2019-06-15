package com.netease.course.neteasecourse.中间件专题.缓存中间件.Redis.Pipleline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Pipeline批量插入，类似于jdbc的批量操作
 * redis的普通插入模式与Pipeline插入模式性能对比
 * <p>
 * 操作完毕：10000
 * 普通模式一万次操作耗时：4654
 * 操作完毕：10000
 * pipeline一万次操作耗时：481
 * <p>
 */
//@Component
public class PipelineTests implements CommandLineRunner {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        // 普通模式和pipeline模式
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            redisTemplate.opsForList().leftPush("queue_1", i);
        }
        System.out.println("操作完毕：" + redisTemplate.opsForList().size("queue_1"));
        System.out.println("普通模式一万次操作耗时：" + (System.currentTimeMillis() - time));

        time = System.currentTimeMillis();
        redisTemplate.executePipelined(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                for (int i = 0; i < 10000; i++) {
                    connection.lPush("queue_2".getBytes(), String.valueOf(i).getBytes());
                }
                return null;
            }
        });
        System.out.println("操作完毕：" + redisTemplate.opsForList().size("queue_2"));
        System.out.println("pipeline一万次操作耗时：" + (System.currentTimeMillis() - time));


    }
}
