package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于自定义协议;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 打印 handler,继承入站事件
 */
@ChannelHandler.Sharable
public class PrintHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 读取 socket连接的请求
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 输出 ByteBuf
        ByteBuf buf = (ByteBuf) msg;
        byte[] content = new byte[buf.readableBytes()];
        buf.readBytes(content);
        System.out.println(Thread.currentThread()+ "：最终打印："+new String(content));
        ((ByteBuf) msg).release(); // 引用计数减一
        //广播通知下一个ChannelHandler继续处理
        // ctx.fireChannelRead();
    }

    // 异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
