package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty框架;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Netty 服务端代码，可从该类跟读Netty源码
 *
 * 跟读NioEventLoopGroup初始化源码：
 *  1.NioEventLoopGroup构造函数根据nThreads数量来初始化一组NioEventLoop
 *  2.NioEventLoop实现了Executor接口，
 *  3.NioEventLoop内部有Selector和taskQueue，其实一个NioEventLoop就对应一个Reactor线程
 *  4.当有线程提交了任务，先将任务添加到taskQueue中，同时判断是否NioEventLoop自身调用，如果不是，则通过传入的Executor执行器创建新线程执行NioEventLoop的run()方法
 *  5.NioEventLoop的run()方法有Selector.select()事件轮询和处理taskQueue中的任务
 *
 * 跟读ServerBootstrap.bind()方法源码：绑定端口并启动Netty服务
 *  1.创建NioServerSocketChannel
 *  2.从NioEventLoopGroup中选择一个NioEventLoop，把Channel注册到该NioEventLoop的Selector上
 *  3.注册是以任务方式提交，当有任务提交时会触发NioEventLoop的run()方法，开始Selector.select()事件轮询
 *  4.doBind0()真正执行绑定端口
 *  5.pipeline.bind() Netty职责链
 *
 * Netty中的ChannelPipeline：
 *  1.当ServerBootstrap.bind()启动Netty时，创建了NioServerSocketChannel，每当有新的Channel创建时会自动创建一个该Channel专有的Pipeline,
 *      Pipeline保存了Channel通道所有Handler处理器，Channel中事件以责任链形式被Pipeline中所有的Handler处理
 *
 **/
public class EchoServer {

    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

    public static void main(String[] args) throws Exception {
        /**
         * 创建NioEventLoopGroup,accept线程组,负责accept请求,内部实质根据nThreads数量，创建多个NioEventLoop。
         * NioEventLoop定义：每一个NioEventLoop对应一个Reactor线程，轮询注册事件的通知，在NioEventLoop中会将Channel感兴趣的事件注册到NioEventLoop中的Selector上
         */
        EventLoopGroup parentGroup = new NioEventLoopGroup(1);
        /**
         * 创建NioEventLoopGroup，I/O线程组，负责接收客户端连接后的IO读写操作
         */
        EventLoopGroup childGroup = new NioEventLoopGroup(1);
        try {
            /**
             * Netty 服务端启动引导工具类
             */
            ServerBootstrap b = new ServerBootstrap();
            /**
             * 配置服务端处理的reactor线程组以及服务端的其他配置
             *
             * .group(parentGroup, childGroup) -> 配合Netty服务端的NioEventLoopGroup，parentGroup负责处理accept,childGroup负责处理具体客户端连接后的读写操作
             * .channel(NioServerSocketChannel.class) -> 服务端创建NioServerSocketChannel通道实例
             * .option(ChannelOption.SO_BACKLOG, 100) -> channel的配置，比较固定
             * .handler(ChannelHandler handler) -> 配置channelHandler来处理服务端通道NioServerSocketChannel的请求
             * .childHandler(ChannelHandler handler) -> 配置channelHandler用来处理具体socket连接后的请求
             */
            b.group(parentGroup, childGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(String.valueOf(LogLevel.DEBUG))).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new EchoServerHandler());
                }
            });
            /**
             * 通过bind启动Netty服务,创建通道并绑定端口
             */
            ChannelFuture f = b.bind(PORT).sync();
            // 阻塞主线程，直到网络服务被关闭
            f.channel().closeFuture().sync();
        } finally {
            // 关闭线程组
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}
