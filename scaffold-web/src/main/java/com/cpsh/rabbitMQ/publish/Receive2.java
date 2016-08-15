package com.cpsh.rabbitMQ.publish;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 在生产者和消费者之间创建一个新的队列，又不想使用原来的队列，临时队列就是为这个场景而生的：
 * 首先，每当我们连接到RabbitMQ，我们需要一个新的空队列，我们可以用一个随机名称来创建，或者说让服务器选择一个随机队列名称给我们。
 * 一旦我们断开消费者，队列应该立即被删除。
 * 
 * 在Java客户端，提供queuedeclare()为我们创建一个非持久化、独立、自动删除的队列名称。
 * 
 * String queueName = channel.queueDeclare().getQueue();
 * 
 * @author Administrator
 * 
 */
public class Receive2 {
    private final static String MQ_HOST = "192.168.230.131";
    private final static int MQ_PORT = 5672;
    private static final String MQ_VHOST = "group_host1";

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {

        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ地址
        factory.setHost(MQ_HOST);
        factory.setPort(MQ_PORT);
        // factory.setUsername("admin");
        // factory.setVirtualHost(MQ_VHOST);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 设置心跳时间
        factory.setRequestedHeartbeat(5);
        // 网络有问题时，好后可自动恢复设置。
        factory.setAutomaticRecoveryEnabled(true);

        // 【fanout】类型创建一个名称为 logs的交换器，
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        /*
         * 创建一个非持久化、独立、自动删除的队列名称
         */
        String queueName = channel.queueDeclare().getQueue();

        /*
         * 绑定关系 ---> 队列 --- 交换器
         * 
         * channel.queueBind()的第三个参数Routing key为空，即所有的消息都接收。 如果这个值不为空，在exchange
         * type为“fanout”方式下该值被忽略！ 即：交换器是分发【fanout】类型，就会忽略路由关键字【routingkey】的作用。
         */
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out
                .println("consumer2----- [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                    AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("consumer2----- [x] Received '" + message
                        + "'");
            }
        };
        //true自动应答
        channel.basicConsume(queueName, true, consumer);
    }
}
