package com.netease.course.neteasecourse.中间件专题.缓存中间件.Redis.数据存储类型与常用命令.geo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * geo 地理位置存储
 * 应用场景：附近的人
 */
@Service
public class GeoExampleService {

    // 参数可以是任何对象，默认由JDK序列化
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 上传地址位置
     */
    public void add(Point point, String userId) {
        //key:user_geo  value:geo
        redisTemplate.opsForGeo().add("user_geo", new RedisGeoCommands.GeoLocation<>(userId, point));
    }

    /**
     * 查找 半径100m的附近5个人
     * @param point 用户自己的位置
     */
    public GeoResults<RedisGeoCommands.GeoLocation> near(Point point) {
        // 半径 100米
        Distance distance = new Distance(100, RedisGeoCommands.DistanceUnit.METERS);
        Circle circle = new Circle(point, distance);
        // 附近5个人
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation> user_geo = redisTemplate.opsForGeo().radius("user_geo", circle, geoRadiusCommandArgs);
        return user_geo;
    }
}