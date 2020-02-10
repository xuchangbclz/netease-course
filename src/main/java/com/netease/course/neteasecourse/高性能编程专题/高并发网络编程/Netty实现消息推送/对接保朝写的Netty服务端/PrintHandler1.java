package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.对接保朝写的Netty服务端;

import com.utopa.netty.model.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 打印 handler,继承入站事件
 */
public class PrintHandler1 extends SimpleChannelInboundHandler<Message> {


    @Override
    protected void channelRead0(ChannelHandlerContext cxt, Message message) throws Exception {
        //System.out.println("客户端接受到服务端消息：" + JSONUtil.toJsonStr(message));
        //System.out.println("客户端接受到服务端Body消息体：" + new String(message.getContent(), CharsetUtil.UTF_8));
        int msgId = message.getMsgId();
        if (msgId == 401) {
            System.out.println("怪物AI攻击玩家：" + new String(message.getContent(), CharsetUtil.UTF_8));
        }
        if (msgId == 400) {
            System.out.println("同步怪物AI信息：" + new String(message.getContent(), CharsetUtil.UTF_8));
        }
    }


}
