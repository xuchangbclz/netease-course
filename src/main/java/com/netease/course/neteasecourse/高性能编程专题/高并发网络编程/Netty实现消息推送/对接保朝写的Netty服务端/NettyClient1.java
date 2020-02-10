package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.对接保朝写的Netty服务端;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.utopa.netty.coder.MessageDecoder;
import com.utopa.netty.coder.MessageEncoder;
import com.utopa.netty.model.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
/**
 * 客户端1
 **/
public class NettyClient1 {

    public static ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);

    public void connect(int port, String host) throws Exception {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new MessageDecoder(1024 * 128, 1, 4));
                            //p.addLast(new PrintHandler1());
                            p.addLast(new MessageEncoder());
                        }
                    });
            ChannelFuture future = b.connect(host, port).sync();
            /**
             * 模拟发消息到服务端
             * 5s发送一次玩家数据到服务器
             */
            //Player playerA = Player.initPlayerA();
            //PlayerMoveTask playerMoveTask = new PlayerMoveTask(playerA,future);
            //scheduledThreadPool.scheduleAtFixedRate(playerMoveTask, 5, 5, TimeUnit.SECONDS);

            //for (int i = 1; i <= 10; i++) {
            //    System.out.println("客户端第" + i + "次发送消息");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("musicId", 2448L);
                jsonObject.put("reqTimeStamp", 15553225632L);
                jsonObject.put("playTime", 15553225632L);
                jsonObject.put("messageType", -1);
                jsonObject.put("pianoId", 7L);
            //
                Message message = Message.buildMessage(1, (byte) 1, (byte) 1, JSONUtil.toJsonStr(jsonObject).getBytes(CharsetUtil.UTF_8));
                future.channel().writeAndFlush(message);
            //    TimeUnit.SECONDS.sleep(60L);
            //}
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws Exception {
        int port = 30000;
        //if (args != null && args.length > 0) {
        //    try {
        //        port = Integer.valueOf(args[0]);
        //    } catch (NumberFormatException e) {
        //        // 采用默认值
        //    }
        //}
        new NettyClient1().connect(port, "192.168.1.113");
    }

}
