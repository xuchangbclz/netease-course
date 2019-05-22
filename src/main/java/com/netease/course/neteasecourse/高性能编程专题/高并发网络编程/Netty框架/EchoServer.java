package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty框架;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 *
 *
 **/
public class EchoServer {

    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

    public static void main(String[] args) throws Exception {
        /**
         * 创建EventLoopGroup,accept线程组,内部实质根据nThreads数量，创建多个NioEventLoop
         * NioEventLoop定义：将通道channel注册到NioEventLoop中的Selector上，进行事件轮询
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // 创建EventLoopGroup   I/O线程组
        EventLoopGroup workerGroup2 = new NioEventLoopGroup(1);
        try {
            // 服务端启动引导工具类
            ServerBootstrap b = new ServerBootstrap();
            // 配置服务端处理的reactor线程组以及服务端的其他配置
            b.group(bossGroup, workerGroup2).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(String.valueOf(LogLevel.DEBUG))).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new EchoServerHandler());
                }
            });
            // 通过bind启动服务
            ChannelFuture f = b.bind(PORT).sync();
            // 阻塞主线程，直到网络服务被关闭
            f.channel().closeFuture().sync();
        } finally {
            // 关闭线程组
            bossGroup.shutdownGracefully();
            workerGroup2.shutdownGracefully();
        }
    }

}
