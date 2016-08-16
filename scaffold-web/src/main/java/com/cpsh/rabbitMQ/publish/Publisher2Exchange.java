package com.cpsh.rabbitMQ.publish;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 交换器的规则有：
 * 
 * direct （直连） topic （主题） headers （标题） fanout （分发）也有翻译为扇出的。
 * 
 * fanout类型广播，无视binding关系，交换器中的消息会分发到所有队列中
 * 
 * 
 * @author Administrator
 * 
 */
public class Publisher2Exchange {

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
        /*
         * 【fanout】类型创建一个名称为 logs的交换器，
         * 
         * 发送消息到队列时根本没有使用交换器，但是消息也能发送到队列。这是因为RabbitMQ选择了一个空“”字符串的默认交换器。
         * channel.basicPublish("", QUEUE_NAME, null,
         * message.getBytes("UTF-8"));
         */

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 分发消息
        for (int i = 0; i < 10; i++) {
            String message = "Hello World! " + i;
            // 消息生产者发送消息到交换器logs中
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }
}
