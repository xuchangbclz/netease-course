package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于WebSocket协议.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;

/**
 * 基于Netty实现客户端
 *
 **/
public class WebSocketClient {

    public static void main(String[] args) throws Exception {
        final String host = System.getProperty("netease.pushserver.host", "127.0.0.1");
        final String maxSize = System.getProperty("netease.client.port.maxSize", "100");
        final String maxConnections = System.getProperty("netease.client.port.maxConnections", "60000");
        int port = 9000;

        EventLoopGroup group = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_REUSEADDR, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    /**
                     * 经过一系类的Handler，socket连接到达服务端
                     */
                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new HttpClientCodec());
                    p.addLast(new HttpObjectAggregator(8192));
                    p.addLast(WebSocketClientCompressionHandler.INSTANCE);
                    p.addLast("webSocketClientHandler", new WebSocketClientHandler());
                }
            });
            // tcp 建立连接
            //for (int i = 0; i < 100; i++) {
            //    for (int j = 0; j < 60000; j++) {
            //        b.connect(host, port).sync().get();
            //    }
            //    port++;
            //}
            b.connect(host, port).sync().get();
            System.in.read();
        } finally {
            group.shutdownGracefully();
        }
    }

}
