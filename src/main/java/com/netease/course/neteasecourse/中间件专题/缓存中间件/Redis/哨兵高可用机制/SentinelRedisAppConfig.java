package com.netease.course.neteasecourse.中间件专题.缓存中间件.Redis.哨兵高可用机制;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * 哨兵连接配置
 */
//@Configuration
class SentinelRedisAppConfig {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        System.out.println("使用哨兵版本");
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
                .master("mymaster")
                // 哨兵地址
                .sentinel("192.168.100.241", 26380)
                .sentinel("192.168.100.241", 26381)
                .sentinel("192.168.100.241", 26382);
        return new LettuceConnectionFactory(sentinelConfig);
    }
}