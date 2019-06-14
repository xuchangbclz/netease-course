package com.netease.course.neteasecourse.中间件专题.消息中间件.RabbitMQ.持久化机制;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ持久化示例
 * message，queue，exchange持久化，配置了持久化后，服务器宕机重启后数据依然存在
 */
public class Producer {

    public static void main(String[] args) {
        // 1、创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2、设置连接属性
        factory.setUsername("order-user");
        factory.setPassword("order-user");
        factory.setVirtualHost("v1");

        // 3、设置每个节点的链接地址和端口
        Address[] addresses = new Address[]{
                new Address("192.168.239.62", 5672),
                new Address("192.168.239.62", 5673),
                new Address("192.168.239.62", 5674)
        };
        Connection connection = null;
        Channel channel = null;

        try {
            // 3、从连接工厂获取连接
            connection = factory.newConnection(addresses,"生产者");

            // 4、从链接中创建通道
            channel = connection.createChannel();

            // 定义一个持久化的，direct类型交换器
            /**
             * @param exchangeName 交换机名
             * @param type 交换机类型
             * @param durable 是否持久化
             */
            channel.exchangeDeclare("routing_test_exchange", "direct", true);

            // 内存、磁盘预警时用
            System.out.println("按回车继续");
            System.in.read();

            // 消息内容
            String message = "Hello A";

            // 发送持久化消息到routing_test交换器上
            /**
             * @param exchangeName 交换机名
             * @param routingKey 路由
             * MessageProperties.PERSISTENT_TEXT_PLAIN 持久化文本消息
             */
            channel.basicPublish("routing_test_exchange", "c1", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("消息 " + message + " 已发送！");

            // 消息内容
            message = "Hello B";
            // 发送持久化消息到routing_test交换器上
            channel.basicPublish("routing_test_exchange", "c2", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("消息 " + message + " 已发送！");

            // 内存、池畔预警时用
            System.out.println("按回车结束");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            // 7、关闭通道
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }

            // 8、关闭连接
            if (connection != null && connection.isOpen()) {
                try {
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
