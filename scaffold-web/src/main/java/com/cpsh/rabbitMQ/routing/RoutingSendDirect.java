package com.cpsh.rabbitMQ.routing;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

/**
 * 通过直连交换器，生产者发送不同路由关键字的日志，消费者端通过绑定自己感兴趣的路由关键字来接收消息，
 * 
 * @author Administrator
 * 
 */
public class RoutingSendDirect {
    private final static String MQ_HOST = "192.168.230.131";
    private final static int MQ_PORT = 5672;

    // 交换器名称
    private static final String EXCHANGE_NAME = "direct_logs";

    // 路由关键字
    private static final String[] routingKeys = new String[] { "info",
            "warning", "error" };

    public static void main(String[] argv) throws Exception {

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ地址
        factory.setHost(MQ_HOST);
        factory.setPort(MQ_PORT);
        // 网络有问题时，好后可自动恢复设置。
        factory.setAutomaticRecoveryEnabled(true);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明交换器---直连方式
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 发送消息
        for (String severity : routingKeys) {
            String message = "Send the message level:" + severity;
            channel.basicPublish(EXCHANGE_NAME, severity, null,
                    message.getBytes());
            System.out
                    .println(" [x] Sent '" + severity + "':'" + message + "'");
        }
        channel.close();
        connection.close();
    }
}
