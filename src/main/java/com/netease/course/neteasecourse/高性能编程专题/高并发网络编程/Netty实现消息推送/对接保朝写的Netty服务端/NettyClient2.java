package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.对接保朝写的Netty服务端;

import com.utopa.netty.coder.MessageDecoder;
import com.utopa.netty.coder.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 客户端2
 **/
public class NettyClient2 {

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
                            p.addLast(new MessageDecoder(131072, 1, 4));
                            p.addLast(new PrintHandler1());
                            p.addLast(new MessageEncoder());
                        }
                    });
            ChannelFuture future = b.connect(host, port).sync();

            Player playerB = Player.initPlayerB();
            PlayerMoveTask playerMoveTask = new PlayerMoveTask(playerB,future);
            scheduledThreadPool.scheduleAtFixedRate(playerMoveTask, 5, 5, TimeUnit.SECONDS);

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws Exception {
        int port = 9011;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new NettyClient2().connect(port, "127.0.0.1");
    }

}
