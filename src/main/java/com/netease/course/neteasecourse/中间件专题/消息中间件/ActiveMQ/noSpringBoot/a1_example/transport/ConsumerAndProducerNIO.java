package com.netease.course.neteasecourse.中间件专题.消息中间件.ActiveMQ.noSpringBoot.a1_example.transport;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

// http://activemq.apache.org/configuring-transports.html
// http://activemq.apache.org/tcp-transport-reference.html

/**
 * NIO 传输协议
 */
public class ConsumerAndProducerNIO {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory = null;
        Connection conn = null;
        Session session = null;
        MessageConsumer consumer = null;

        try {
            // 1、创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory("nio://activemq.tony.com:61616");
            // 2、创建连接对象
            conn = connectionFactory.createConnection();
            conn.start();
            // 3、 创建session
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 4、创建点对点接收的目标
            Destination destination = session.createQueue("queue1");
            // 5、创建生产者消息
            MessageProducer producer = session.createProducer(destination);
            // 设置生产者的模式，有两种可选
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            // 6、创建一条消息
            String text = "Hello world!";
            TextMessage message = session.createTextMessage(text);
            // 7、发送消息
            producer.send(message);
            // 8、创建消费者消息
            consumer = session.createConsumer(destination);
            // 9、接收消息
            Message consumerMessage = consumer.receive();
            if (consumerMessage instanceof TextMessage) {
                System.out.println("收到文本消息：" + ((TextMessage) consumerMessage).getText());
            } else {
                System.out.println(consumerMessage);
            }

            consumer.close();
            session.close();
            conn.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
