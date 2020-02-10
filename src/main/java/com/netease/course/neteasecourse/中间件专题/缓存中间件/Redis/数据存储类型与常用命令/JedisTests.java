package com.netease.course.neteasecourse.中间件专题.缓存中间件.Redis.数据存储类型与常用命令;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Jedis客户端测试不同redis存储数据类型：String,List,Hash,Set,Zset,Geo，BitMap,HyperLogLog(地理位置坐标)，
 *
 * BitMap使用场景：
 *  就是通过一个bit位来表示某个元素对应的值或者状态,其中的key就是对应元素本身。我们知道8个bit可以组成一个Byte，所以bitmap本身会极大的节省储存空间
 *
 * 统计网站每个网页每天的 PV 数据
 *  给每个网页一个独立的 Redis 计数器就可以了，这个计数器的 key 后缀加上当天的日期。这样来一个请求，incrby 一次，最终就可以统计出所有的 PV 数据
 *
 * 统计网站每个网页每天的 UV 数据
 *  https://www.cnblogs.com/yourscgg/p/9463660.html
 *
 *
 **/
public class JedisTests {

    private static final Jedis jedis = new Jedis("127.0.0.1", 6379);

    public static void main(String[] args) {
        //list();
        //hashTest();
        setTest();
    }


    /**
     * List 集合数据存储~
     * 应用场景：模拟队列，生产者消费者（简单MQ）
     */
    public static void list() {
        // 插入数据1 --- 2 --- 3
        jedis.rpush("queue_1", "1");
        jedis.rpush("queue_1", "2", "3");
        //遍历
        List<String> strings = jedis.lrange("queue_1", 0, -1);
        for (String string : strings) {
            System.out.println(string);
        }

        // 消费者线程
        while (true) {
            //弹出操作
            String item = jedis.lpop("queue_1");
            if (item == null) break;
            System.out.println(item);
        }
        jedis.close();
    }


    /**
     * Hash存储类型，类似：在redis里面存储一个hashmap
     * 推荐的方式，无特殊需求是，一般的缓存都用这个
     */
    public static void hashTest() {
        HashMap<String, Object> user = new HashMap<>();
        user.put("name", "tony");
        user.put("age", 18);
        user.put("userId", 10001);
        //插入
        jedis.hset("user_10001", "name", "tony");
        jedis.hset("user_10001", "age", "18");
        jedis.hset("user_10001", "userId", "10001");

        System.out.println(jedis.hget("user_10001", "name"));
        System.out.println(jedis.hget("user_10001", "xxx"));
        System.out.println(jedis.hgetAll("user_10001"));
        jedis.close();
    }


    /**
     * 用set实现（交集 |并集）
     * 应用场景：共同关注的好友
     */
    public static void setTest() {
        // 每个人维护一个set
        jedis.sadd("user_A", "userC", "userD", "userE");
        jedis.sadd("user_B", "userC", "userE", "userF");
        // 交集，取出两个人共同关注的好友
        Set<String> sinter = jedis.sinter("user_A", "user_B");
        System.out.println(sinter);

        // 并集，检索给某一个帖子点赞和转发的
        jedis.sadd("trs_tp_1001", "userC", "userD", "userE");
        jedis.sadd("star_tp_1001", "userE", "userF");
        // 取出共同人群
        Set<String> union = jedis.sunion("star_tp_1001", "trs_tp_1001");
        System.out.println(union);

        jedis.close();
    }


    /**
     * Sorted set存储数据类型
     * 应用场景：排行榜
     */
    public static void zsetTest() {
        String ranksKeyName = "exam_rank";
        jedis.zadd(ranksKeyName, 100.0, "tony");
        jedis.zadd(ranksKeyName, 82.0, "allen");
        jedis.zadd(ranksKeyName, 90, "mengmeng");
        jedis.zadd(ranksKeyName, 96, "netease");
        jedis.zadd(ranksKeyName, 89, "ali");
        //排序
        Set<String> stringSet = jedis.zrevrange(ranksKeyName, 0, 2);
        System.out.println("返回前三名:");
        for (String s : stringSet) {
            System.out.println(s);
        }

        Long zcount = jedis.zcount(ranksKeyName, 85, 100);
        System.out.println("超过85分的数量 " + zcount);

        jedis.close();
    }

}
