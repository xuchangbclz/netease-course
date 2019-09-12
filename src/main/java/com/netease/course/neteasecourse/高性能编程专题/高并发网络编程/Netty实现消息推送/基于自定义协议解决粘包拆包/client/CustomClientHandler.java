package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于自定义协议解决粘包拆包.client;

import com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于自定义协议解决粘包拆包.server.CustomMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 **/
public class CustomClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CustomMsg customMsg = new CustomMsg((byte)0xAB, (byte)0xCD, "Hello,Netty".length(), "Hello,Netty");
        ctx.writeAndFlush(customMsg);
    }

}
