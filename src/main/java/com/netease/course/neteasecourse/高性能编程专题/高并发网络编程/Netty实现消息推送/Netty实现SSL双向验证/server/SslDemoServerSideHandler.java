package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.Netty实现SSL双向验证.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author daituo
 * @Date
 **/
@Slf4j
public class SslDemoServerSideHandler extends SimpleChannelInboundHandler<String> {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        log.info("服务端连接建立，Connection Created from {}", ctx.channel().remoteAddress());
        ctx.pipeline().get(SslHandler.class).handshakeFuture()
                .addListener(new GenericFutureListener<Future<Channel>>() {
                    @Override
                    public void operationComplete(Future<Channel> future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("------>握手成功");
                            System.out.println("------->" + "Your session is protected by "+ ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite());
                        } else {
                            System.out.println("------>握手失败");
                        }
                    }
                });
        SocketUtils.sendHello(ctx, "server", false);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // Send the received message to all channels but the current one.
        log.info("服务端读到数据：ip:{} --- msg:{}", ctx.channel().remoteAddress(), msg);

        //String reply = "Server counter " + counter.getAndAdd(1);
        //SocketUtils.sendLineBaseText(ctx, reply);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.warn("Unexpected exception from downstream.", cause);
        ctx.close();
    }

    //@Override
    //public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
    //        throws Exception {
    //    if (evt instanceof IdleStateEvent) {
    //        IdleStateEvent event = (IdleStateEvent) evt;
    //        if (event.state().equals(IdleState.READER_IDLE)) {
    //            log.info("READER_IDLE");
    //            // 超时关闭channel
    //            ctx.close();
    //        } else if (event.state().equals(IdleState.WRITER_IDLE)) {
    //            log.info("WRITER_IDLE");
    //        } else if (event.state().equals(IdleState.ALL_IDLE)) {
    //            log.info("ALL_IDLE");
    //            // 发送心跳
    //            ctx.channel().write("ping\n");
    //        }
    //    }
    //    super.userEventTriggered(ctx, evt);
    //}

}
