package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于protobuf解决高性能序列化压缩.client;

import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @Description TODO
 * @Author daituo
 * @Date
 **/
public class MyProtoBufEncoder extends MessageToMessageEncoder<MessageLiteOrBuilder> {

    public MyProtoBufEncoder() {
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageLiteOrBuilder msg, List<Object> out) throws Exception {
        if (msg instanceof MessageLite) {
            out.add(Unpooled.wrappedBuffer(((MessageLite)msg).toByteArray()));
            System.out.println("Protobuf 编码后的byte长度：" +((MessageLite)msg).toByteArray().length);
        } else {
            if (msg instanceof MessageLite.Builder) {
                out.add(Unpooled.wrappedBuffer(((MessageLite.Builder)msg).build().toByteArray()));
                System.out.println("Protobuf 编码后的byte长度：" + ((MessageLite.Builder)msg).build().toByteArray().length) ;
            }

        }
    }
}
