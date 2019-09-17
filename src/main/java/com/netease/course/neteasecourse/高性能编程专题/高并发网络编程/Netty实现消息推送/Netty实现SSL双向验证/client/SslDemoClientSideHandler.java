package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.Netty实现SSL双向验证.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description TODO
 * @Author daituo
 * @Date
 **/
@Slf4j
public class SslDemoClientSideHandler extends SimpleChannelInboundHandler<String> {


    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        log.info("-----> 客户端建立连接");
        //SocketUtils.sendHello(ctx,"Client", false);

        //String str20 = "012345678901234567890123456789";
        //ctx.writeAndFlush(str20);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // Send the received message to all channels but the current one.
        log.info("客户端收到数据：ip:{} --- msg:{}", ctx.channel().remoteAddress(), msg);
        //String reply = "Client side currentTime:" + LocalDateTime.now().toString();
        //SocketUtils.sendLineBaseText(ctx, reply);

        String str20 = "012345678901234567890123456789";
        ctx.writeAndFlush(str20);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }

}
