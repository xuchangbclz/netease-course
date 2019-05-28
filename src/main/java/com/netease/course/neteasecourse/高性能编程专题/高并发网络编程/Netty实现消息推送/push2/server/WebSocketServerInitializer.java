package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.push2.server;

import com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.push2.handler.NewConnectHandler;
import com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.push2.handler.WebSocketServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 *
 **/
public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        //  职责链， 数据处理流程
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec()); //
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerHandler());
        pipeline.addLast(new NewConnectHandler());
    }

}
