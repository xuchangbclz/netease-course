package com.netease.course.neteasecourse.中间件专题.缓存中间件.Redis.哨兵高可用机制;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 哨兵高可用 测试，在运行期间，停止redis-master服务,看看是否能够故障转移
 */
public class SentinelTests implements CommandLineRunner {

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    @Override
    public void run(String... args) throws Exception {
        // 每一秒钟，操作一下redis，看看最终效果
        int i = 0;
        while (true) {
            i++;
            stringRedisTemplate.opsForValue().set("test-value", String.valueOf(i));
            System.out.println("修改test-value值为: " + i);
            Thread.sleep(1000L);
        }

    }
}
