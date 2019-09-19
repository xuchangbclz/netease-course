package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.Netty实现SSL双向验证.server;

import cn.hutool.json.JSONUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @Author daituo
 * @Date
 **/
@Slf4j
public class SocketUtils {

    public static void sendHello(ChannelHandlerContext ctx, String from, boolean isSecure){
        if( null != ctx) {
            send(ctx, "HELLO from " + from + System.getProperty("line.separator") + "Your session is protected by "+ ctx.pipeline().get(SslHandler.class).engine().getSession().getCipherSuite());
        }
    }

    public static void sendLineBaseText(ChannelHandlerContext ctx, String text){
        if( null != ctx){
            send(ctx, text + System.getProperty("line.separator"));
        }
    }

    /*
     *
     */
    private static void send(ChannelHandlerContext ctx, String log){
        if( null != ctx && null != log){
            ByteBuf msgBuf = Unpooled.buffer(log.length());
            msgBuf.writeBytes(log.getBytes(StandardCharsets.UTF_8));
            ctx.writeAndFlush(msgBuf);
        }
    }

}
