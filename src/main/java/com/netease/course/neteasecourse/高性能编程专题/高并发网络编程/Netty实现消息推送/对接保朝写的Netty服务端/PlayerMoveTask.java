package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.对接保朝写的Netty服务端;

import cn.hutool.json.JSONUtil;
import com.utopa.netty.service.model.Message;
import io.netty.channel.ChannelFuture;
import io.netty.util.CharsetUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author daituo
 * @Date
 **/
@Slf4j
@Data
public class PlayerMoveTask implements Runnable{

    private Player player;

    private ChannelFuture future;

    private AtomicInteger count = new AtomicInteger(0);

    public PlayerMoveTask(Player player, ChannelFuture future) {
        this.player = player;
        this.future = future;
    }

    @Override
    public void run() {
        int i = count.getAndIncrement();
        if (i % 20 >= 0 && i % 20 < 9) {
            player.setXAxis(player.getXAxis() + 200);
        } else {
            player.setXAxis(player.getXAxis() - 200);
        }
        Message message = Message.buildMessage(200, (byte) 1, (byte) 1, JSONUtil.toJsonStr(player).getBytes(CharsetUtil.UTF_8));
        future.channel().writeAndFlush(message);
    }
}
