package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于自定义协议;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 普通的SocketServer服务端
 *  1.服务端打印客户端请求内容，这里同一个Socket请求连续发送10条消息，如果服务端仅仅按照固定字节来读取，会出现粘包，
 *      使得服务端无法解析每次发送过来的消息内容
 *
 */
public class TonySocketServer {
    public static void main(String[] args) throws IOException, Exception {
        // server
        ServerSocket serverSocket = new ServerSocket(9999);

        // 获取新连接
        while (true) {
            final Socket accept = serverSocket.accept();
            // accept.getOutputStream().write("推送实例");
            InputStream inputStream = accept.getInputStream();
            while (true) {
                //TODO 注意服务端固定读取1024字节
                byte[] request = new byte[1024];
                int read = inputStream.read(request);
                if (read == -1) {
                    break;
                }
                // 得到请求内容，解析，得到发送对象和发送内容
                String content = new String(request);
                if(content.getBytes().length > 220) {
                   // TODO
                } else if(content.getBytes().length < 220) {

                }
                // 每次读取到的数据，不能够保证是一条完整的信息
                System.out.println(content);
            }
        }
    }
}
