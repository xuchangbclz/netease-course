package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.netty心跳实现.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 客户端
 **/
public class HeartBeatsClient {


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
                            /**
                             * pipeline中handler顺序
                             *      IdleStateHandler - ChannelDuplexHandler - 1
                             *      StringDecoder - ChannelInboundHandler - 2
                             *      StringEncoder - ChannelOutboundHandler - 3
                             *      HeartBeatClientHandler - ChannelInboundHandler - 4
                             *
                             * 执行顺序：
                             *      入站事件 -> 1,2,4
                             *      出站事件 -> 3,1
                             *
                             * 客户端代码也要加入IdleStateHandler这个handler，注意的是，我们要注意的是写超时，所以要设置写超时的时间，因为服务器端是5秒检测读超时，
                             * 所以客户端必须在5秒内发送一次心跳，告之服务端，所以我们设置4秒
                             */
                            //p.addLast("ping", new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
                            //p.addLast("decoder", new StringDecoder());
                            //p.addLast("encoder", new StringEncoder());
                            p.addLast(new HeartBeatClientHandler());
                        }
                    });
            ChannelFuture future = b.connect(host, port).sync();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }


    public static void main(String[] args) throws Exception {
        int port = 9011; //8080
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        new HeartBeatsClient().connect(port, "127.0.0.1");
    }

}
