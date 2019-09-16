package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.对接保朝写的netty服务端;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.utopa.netty.service.coder.MessageDecoder;
import com.utopa.netty.service.coder.MessageEncoder;
import com.utopa.netty.service.model.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * 客户端1
 **/
public class NettyClient1 {


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
            /**
             * 模拟连续发消息到服务端
             */
            for (int i = 1; i <= 10; i++) {
                System.out.println("客户端第" + i + "次发送消息");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("musicId", 2448L);
                jsonObject.put("reqTimeStamp", 15553225632L);
                jsonObject.put("playTime", 15553225632L);
                jsonObject.put("messageType", -1);
                jsonObject.put("pianoId", 7L);

                Message message = Message.buildMessage(1, (byte) 1, (byte) 1, JSONUtil.toJsonStr(jsonObject).getBytes(CharsetUtil.UTF_8));
                future.channel().writeAndFlush(message);
                TimeUnit.SECONDS.sleep(60L);
            }
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
        new NettyClient1().connect(port, "127.0.0.1");
    }

}
