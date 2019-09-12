package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于protobuf解决高性能序列化压缩.server;

import com.netease.course.neteasecourse.RichManProto;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.List;

/**
 *
 **/
public class ProtoBufServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        //RichManProto.RichMan req = (RichManProto.RichMan) msg;
        //System.out.println(req.getName()+"他有"+req.getCarsCount()+"量车");
        //List<RichManProto.RichMan.Car> lists = req.getCarsList();
        //if(null != lists) {
        //
        //    for(RichManProto.RichMan.Car car : lists){
        //        System.out.println(car.getName());
        //    }
        //}
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
