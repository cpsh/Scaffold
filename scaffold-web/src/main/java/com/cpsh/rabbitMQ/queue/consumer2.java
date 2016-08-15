package com.cpsh.rabbitMQ.queue;

import java.io.IOException;

import com.rabbitmq.client.*;

public class consumer2 {

    private static final String TASK_QUEUE_NAME = "task_queue";
    private final static String MQ_HOST = "192.168.230.131";
    private final static int MQ_PORT = 5672;
    private final static int prefetchCount = 1;// 每次从队列中获取数量

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(MQ_HOST);
        factory.setPort(MQ_PORT);

        // 设置心跳时间
        factory.setRequestedHeartbeat(5);
        // 网络有问题时，好后可自动恢复设置。
        factory.setAutomaticRecoveryEnabled(true);

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        System.out
                .println("Worker2 [*] Waiting for messages. To exit press CTRL+C");
        /*
         * 默认情况下，RabbitMQ将队列消息随机分配给每个消费者，这时可能出现消息调度不均衡的问题
         * RabbitMQ此时只负责调度消息，不会根据ACK的反馈机制来分析哪台服务器返回反馈慢
         * 
         * 为了解决这个问题，我们可以使用【prefetchcount =1】这个设置。
         * 这个设置告诉RabbitMQ，不要一次将多个消息发送给一个消费者。
         * 这样做的好处是只有当消费者处理完成当前消息并反馈后，才会收到另外一条消息或任务。这样就避免了负载不均衡的事情了。
         */

        channel.basicQos(prefetchCount);

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                    AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println("Worker2 [x] Received '" + message + "'");
                try {
                    doWork(message);
                } finally {
                    System.out.println("Worker2 [x] Done");
                    // 消息处理完成确认
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // 消息消费完成确认
        channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    }

    private static void doWork(String task) {
        try {
            Thread.sleep(1000); // 暂停1秒钟
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
