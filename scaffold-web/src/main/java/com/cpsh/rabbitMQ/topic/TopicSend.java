package com.cpsh.rabbitMQ.topic;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

/**
 * 路由关键字【routingKey】不能超过255个字节（bytes） 匹配交换器的匹配符
 * 
 * *（星号）表示一个单词
 * #（井号）表示零个或者多个单词
 * 
 * 交换器在匹配模式下：
 * 
 * 如果消费者端的路由关键字只使用【#】来匹配消息，在匹配【topic】模式下，它会变成一个分发【fanout】模式，接收所有消息。
 * 
 * 如果消费者端的路由关键字中没有【#】或者【*】，它就变成直连【direct】模式来工作
 */

public class TopicSend {
    private final static String MQ_HOST = "192.168.230.131";
    private final static int MQ_PORT = 5672;
    // 交换器名称
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {
            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            // 设置RabbitMQ地址
            factory.setHost(MQ_HOST);
            factory.setPort(MQ_PORT);

            connection = factory.newConnection();
            channel = connection.createChannel();
            // 声明一个匹配模式的交换器
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            // 待发送的消息
            String[] routingKeys = new String[] { "quick.orange.rabbit",
                    "lazy.orange.elephant", "quick.orange.fox",
                    "lazy.brown.fox", "quick.brown.fox",
                    "quick.orange.male.rabbit", "lazy.orange.male.rabbit" };
            // 发送消息
            for (String routingKey : routingKeys) {
                String message = "From " + routingKey + " routingKey' s message!";
                channel.basicPublish(EXCHANGE_NAME, routingKey, null,
                        message.getBytes());
                System.out.println("TopicSend [x] Sent '" + routingKey + "':'"
                        + message + "'");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}
