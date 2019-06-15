package com.netease.course.neteasecourse.中间件专题.缓存中间件.Redis.数据存储类型与常用命令.geo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.stereotype.Component;

/**
 * 查找附近的人  测试
 */
@Component
public class GeoTests implements CommandLineRunner {

    @Autowired
    GeoExampleService geoExampleService;

    @Override
    public void run(String... args) throws Exception {
        // 模拟三个人位置上传
        geoExampleService.add(new Point(116.405285, 39.904989), "allen");
        geoExampleService.add(new Point(116.405265, 39.904969), "mike");
        geoExampleService.add(new Point(116.405315, 39.904999), "tony");

        // tony查找附近的人
        GeoResults<RedisGeoCommands.GeoLocation> geoResults = geoExampleService.near(new Point(116.405315, 39.904999));
        for (GeoResult<RedisGeoCommands.GeoLocation> geoResult : geoResults) {
            RedisGeoCommands.GeoLocation content = geoResult.getContent();
            System.out.println(content.getName() + " :" + geoResult.getDistance().getValue());
        }

    }
}
