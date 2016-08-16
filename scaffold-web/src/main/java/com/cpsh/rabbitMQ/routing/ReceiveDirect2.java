package com.cpsh.rabbitMQ.routing;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 通过直连交换器，生产者发送不同路由关键字的日志，消费者端通过绑定自己感兴趣的路由关键字来接收消息，
 * @author Administrator
 *
 */
public class ReceiveDirect2 {
    private final static String MQ_HOST = "192.168.230.131";
    private final static int MQ_PORT = 5672;

    // 交换器名称
    private static final String EXCHANGE_NAME = "direct_logs";

    // 路由关键字
    private static final String[] routingKeys = new String[] { "error","warning" };

    public static void main(String[] argv) throws Exception {

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ地址
        factory.setHost(MQ_HOST);
        factory.setPort(MQ_PORT);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明交换器---直连方式
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        // 获取匿名队列名称
        String queueName = channel.queueDeclare().getQueue();
        
        // 根据路由关键字进行多重绑定--->队列接收到绑定routing-key所对应的消息
        for (String severity : routingKeys) {
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
            System.out.println("ReceiveLogsDirect2 exchange:" + EXCHANGE_NAME
                    + ", queue:" + queueName + ", BindRoutingKey:" + severity);
        }
        System.out
                .println("ReceiveLogsDirect2 [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                    AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("ReceiveLogsDirect2 [x] Received '"
                        + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        //true自动应答
        channel.basicConsume(queueName, true, consumer);
    }
}
