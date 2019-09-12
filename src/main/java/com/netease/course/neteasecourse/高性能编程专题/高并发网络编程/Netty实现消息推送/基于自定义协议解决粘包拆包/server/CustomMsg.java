package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于自定义协议解决粘包拆包.server;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 自定义协议类
 *
 **/
@Data
@AllArgsConstructor
public class CustomMsg {

    //类型  系统编号 0xAB 表示A系统，0xBC 表示B系统
    private byte type;

    //信息标志  0xAB 表示心跳包    0xBC 表示超时包  0xCD 业务信息包
    private byte flag;

    //主题信息的长度
    private int length;

    //主题信息
    private String body;


}
