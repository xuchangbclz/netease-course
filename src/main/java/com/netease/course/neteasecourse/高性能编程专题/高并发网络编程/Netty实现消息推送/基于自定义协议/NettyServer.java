package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于自定义协议;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty实现的服务端
 *  1.Netty服务端也会出现粘包和拆包，因此需要自定义客户端与服务端的传输数据协议，帮助服务端解析传输数据
 *
 * WebSocket协议，基于TCP协议，实现了浏览器与服务器全双工通信，允许服务器主动推送消息给客户端，
 *      http协议属于半双工，服务端只有接受请求后才能响应，而无法主动推送
 *
 */
public class NettyServer {
    public static void main(String[] args) throws Exception {
        // 1、 线程定义
        // accept 处理连接的线程池
        EventLoopGroup acceptGroup = new NioEventLoopGroup();
        // read io 处理数据的线程池
        EventLoopGroup readGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(acceptGroup, readGroup);
            // 2、 选择TCP协议，NIO的实现方式
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 3、 职责链定义（请求收到后怎么处理，每个socket连接有一套自己的ChannelPipeline）
                    ChannelPipeline pipeline = ch.pipeline();
                    // TODO 3.1 增加解码器handler
                    //pipeline.addLast(new DecoderHandler());

                    // TODO 3.2 打印出内容 handler
                    pipeline.addLast(new PrintHandler());
                }
            });
            // 4、 绑定端口
            System.out.println("启动成功，端口 9999");
            b.bind(9999).sync().channel().closeFuture().sync();
        } finally {
            acceptGroup.shutdownGracefully();
            readGroup.shutdownGracefully();
        }
    }
}
