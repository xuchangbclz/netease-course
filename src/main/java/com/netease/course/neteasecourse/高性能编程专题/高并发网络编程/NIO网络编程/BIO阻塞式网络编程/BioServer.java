package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.NIO网络编程.BIO阻塞式网络编程;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * bug:当有多个客户端需要建立连接时，由于阻塞，使得服务端接受不到新连接
 *
 **/
public class BioServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("服务器启动成功");
        while (!serverSocket.isClosed()) {
            /** accept()阻塞 */
            Socket request = serverSocket.accept();
            System.out.println("收到新连接 : " + request.toString());
            try {
                // 接收数据、打印
                InputStream inputStream = request.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                String msg;
                /** BufferedReader读阻塞，没有数据 */
                while ((msg = reader.readLine()) != null) {
                    if (msg.length() == 0) {
                        break;
                    }
                    System.out.println(msg);
                }
                System.out.println("收到数据,来自："+ request.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    request.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        serverSocket.close();
    }

}
