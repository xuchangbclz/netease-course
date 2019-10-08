package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.对接保朝写的Netty服务端;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 游戏玩家
 * @Author daituo
 * @Date
 **/
@Data
@Accessors(chain = true)
public class Player implements Serializable {

    private Long id;

    private String name;

    private Integer xAxis;

    private Integer yAxis;

    /**
     * 玩家距离怪物距离
     */
    private Integer distanceWithMonster;

    public static Player initPlayerA() {
        return new Player().setId(1L).setName("玩家A").setXAxis(200).setYAxis(700);
    }

    public static Player initPlayerB() {
        return new Player().setId(2L).setName("玩家B").setXAxis(200).setYAxis(900);
    }
}
