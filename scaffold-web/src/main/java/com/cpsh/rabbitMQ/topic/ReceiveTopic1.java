package com.cpsh.rabbitMQ.topic;

import com.rabbitmq.client.*;

import java.io.IOException;

public class ReceiveTopic1 {
    private final static String MQ_HOST = "192.168.230.131";
    private final static int MQ_PORT = 5672;
    // 交换器名称
    private static final String EXCHANGE_NAME = "topic_logs";

    private static final String TOPIC_QUEUE = "topic_queue1";

    public static void main(String[] argv) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ地址
        factory.setHost(MQ_HOST);
        factory.setPort(MQ_PORT);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 声明一个匹配模式的交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // 创建临时队列
        // String TOPIC_QUEUE = channel.queueDeclare().getQueue();
        channel.queueDeclare(TOPIC_QUEUE, false, false, false, null);
        
        
        // 路由关键字
        String[] routingKeys = new String[] { "*.orange.*","*","#.*" };
        // 绑定路由关键字
        for (String bindingKey : routingKeys) {
            channel.queueBind(TOPIC_QUEUE, EXCHANGE_NAME, bindingKey);

            System.out.println("ReceiveTopic1 exchange:" + EXCHANGE_NAME
                    + ", queue:" + TOPIC_QUEUE + ", BindRoutingKey:"
                    + bindingKey);
        }

        System.out
                .println("ReceiveTopic1 [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                    AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("ReceiveTopic1 [x] Received '"
                        + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        channel.basicConsume(TOPIC_QUEUE, true, consumer);
    }
}
