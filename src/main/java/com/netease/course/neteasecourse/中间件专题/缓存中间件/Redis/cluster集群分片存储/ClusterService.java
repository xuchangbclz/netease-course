package com.netease.course.neteasecourse.中间件专题.缓存中间件.Redis.cluster集群分片存储;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("a7_cluster")
public class ClusterService {
    @Autowired
    private StringRedisTemplate template;

    public void set(String userId, String userInfo) {
        template.opsForValue().set(userId, userInfo);
    }
}
