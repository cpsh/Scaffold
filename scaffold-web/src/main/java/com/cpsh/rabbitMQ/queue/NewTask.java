package com.cpsh.rabbitMQ.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {

    private static final String TASK_QUEUE_NAME = "task_queue";
    private final static String MQ_HOST = "192.168.230.131";
    private final static int MQ_PORT = 5672;

    public static void main(String[] argv) throws java.io.IOException,
            Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(MQ_HOST);
        factory.setPort(MQ_PORT);
        // 网络有问题时，好后可自动恢复设置。
        factory.setAutomaticRecoveryEnabled(true);

        // 设置心跳时间
        factory.setRequestedHeartbeat(5);
        // 网络有问题时，好后可自动恢复设置。
        factory.setAutomaticRecoveryEnabled(true);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 使"task_queue"的Queue的durable的属性为true，即使消息队列durable

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        // 分发消息

        /*
         * 关于消息持久化的说明
         * 标记为持久化后的消息也不能完全保证不会丢失。虽然已经告诉RabbitMQ消息要保存到磁盘上，但是理论上，RabbitMQ已经接收到生产者的消息
         * ，但是还没有来得及保存到磁盘上，服务器就挂了（比如机房断电），那么重启后，RabbitMQ中的这条未及时保存的消息就会丢失。
         * 因为RabbitMQ不做实时立即的磁盘同步
         * （fsync）。这种情况下，对于持久化要求不是特别高的简单任务队列来说，还是可以满足的。如果需要更强大的保证
         * ，那么你可以考虑使用生产者确认反馈机制
         */
        for (int i = 0; i < 50; i++) {
            String message = "Hello World! " + i;

            /*
             * rabbitMQ选择了一个空""字符串的默认交换器第一个参数就是交换器的名称。如果输入""空字符串，表示使用默认的匿名交换器。
             * 第二个参数是【routingKey】路由线索 匿名交换器规则： 发送到routingKey名称对应的队列。
             * MessageProperties.PERSISTENT_TEXT_PLAIN : 使消息durable持久化
             */
            channel.basicPublish("", TASK_QUEUE_NAME,
                    MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }

}
