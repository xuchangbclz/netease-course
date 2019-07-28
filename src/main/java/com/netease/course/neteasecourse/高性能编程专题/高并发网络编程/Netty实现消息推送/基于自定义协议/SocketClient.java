package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于自定义协议;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;


/**
 * 模拟1个客户端连续发送10条消息给服务端
 *
 **/
public class SocketClient {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 9999);
        OutputStream outputStream = socket.getOutputStream();

        /**
         * 客户端发送服务端消息内容【长度固定为 220字节】：
         * 1. 目标用户ID长度为10， 10 000 000 000 ~ 19 999 999 999
         * 2. 消息内容字符串长度最多70。 按一个汉字3字节，内容的最大长度为210字节
         */
        byte[] request = new byte[220];
        byte[] userId = "10000000000".getBytes();
        byte[] content = "我爱你tony你爱我吗?我爱你tony你爱我吗?我爱你tony你爱我吗?我爱你tony你爱我吗?".getBytes();
        System.arraycopy(userId, 0, request, 0, 10);
        System.arraycopy(content, 0, request, 10, content.length);

        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    outputStream.write(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }).start();
        }

        countDownLatch.await();
        Thread.sleep(2000L); // 两秒后退出
        socket.close();
    }

}
