package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于protobuf解决高性能序列化压缩.client;

import com.netease.course.neteasecourse.RichManProto;
import com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于protobuf解决高性能序列化压缩.RichManProtoCopy;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 **/
public class ProtoBufClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("----------> 客户端连接建立");
        /**
         * 使用protobuf序列化
         */
        RichManProto.RichMan.Builder builder = RichManProto.RichMan.newBuilder();
        builder.setName("王思聪");
        builder.setId(1);
        builder.setEmail("wsc@163.com");

        List<RichManProto.RichMan.Car> cars = new ArrayList<RichManProto.RichMan.Car>();
        RichManProto.RichMan.Car car1 = RichManProto.RichMan.Car.newBuilder().setName("上海大众超跑").setType(RichManProto.RichMan.CarType.DASAUTO).build();
        RichManProto.RichMan.Car car2 = RichManProto.RichMan.Car.newBuilder().setName("Aventador").setType(RichManProto.RichMan.CarType.LAMBORGHINI).build();
        RichManProto.RichMan.Car car3 = RichManProto.RichMan.Car.newBuilder().setName("奔驰SLS级AMG").setType(RichManProto.RichMan.CarType.BENZ).build();

        cars.add(car1);
        cars.add(car2);
        cars.add(car3);

        builder.addAllCars(cars);
        ctx.writeAndFlush(builder.build());

        /**
         * 使用原生序列化
         */
        //RichManProtoCopy richManProtoCopy= new RichManProtoCopy();
        //richManProtoCopy.setId(1);
        //richManProtoCopy.setName("王思聪");
        //richManProtoCopy.setEmail("wsc@163.com");
        //List<RichManProtoCopy.Car> cars = new ArrayList<>();
        //RichManProtoCopy.Car car = new RichManProtoCopy.Car();
        //car.setName("上海大众超跑");
        //car.setType(RichManProtoCopy.CarType.DASAUTO);
        //
        //RichManProtoCopy.Car car1 = new RichManProtoCopy.Car();
        //car1.setName("Aventador");
        //car1.setType(RichManProtoCopy.CarType.LAMBORGHINI);
        //
        //RichManProtoCopy.Car car2 = new RichManProtoCopy.Car();
        //car2.setName("奔驰SLS级AMG");
        //car2.setType(RichManProtoCopy.CarType.BENZ);
        //
        //cars.add(car);
        //cars.add(car1);
        //cars.add(car2);
        //richManProtoCopy.setCars(cars);
        ///**
        // * 发送数据需要封装成ByteBuf
        // */
        //
        //byte[] bytes = richManProtoCopy.toString().getBytes();
        //System.out.println("原生序列化byte长度：" + bytes.length);
        //ByteBuf byteBuf = Unpooled.buffer(bytes.length);
        //byteBuf.writeBytes(bytes);
        //ctx.writeAndFlush(byteBuf);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
