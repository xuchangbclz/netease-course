package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.NIO网络编程.BIO阻塞式网络编程;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @Author daituo
 * @Date
 **/
public class BioClient {

    private static Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 8080);
        OutputStream out = socket.getOutputStream();

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入：");
        String msg = scanner.nextLine();
        /** OutputStream写阻塞，直到写完成 */
        out.write(msg.getBytes(charset));
        scanner.close();
        socket.close();
    }

}
