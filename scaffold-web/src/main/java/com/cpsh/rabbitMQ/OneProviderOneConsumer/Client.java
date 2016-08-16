package com.cpsh.rabbitMQ.OneProviderOneConsumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    private final static String QUEUE_NAME = "helloword_queue";
    private final static String MQ_HOST = "192.168.230.131";
    private final static int MQ_PORT = 5672;

    public static void main(String[] args) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ地址
        factory.setHost(MQ_HOST);
        factory.setPort(MQ_PORT);
/*        // 网络有问题时，好后可自动恢复设置。
        factory.setAutomaticRecoveryEnabled(true);
        // 网络有问题时，好后可自动恢复设置。
        factory.setAutomaticRecoveryEnabled(true);

  */     
        

        // 消息者线程池:消费者时使用，自动开了一20个线程的池来搞
        ExecutorService es = Executors.newFixedThreadPool(20);
//        Connection connection = factory.newConnection(es);

        //地址数组: 上述代码如果连hostname1失败了就去hostname2。 factory.newConnection()会触发这个检测。
        Address[] addrArr = new Address[] {
                new Address("192.168.230.131", 5672),
                new Address("192.168.230.133", 5672) };
        Connection connection = factory.newConnection(addrArr);

//        Connection connection = factory.newConnection(es, addrArr);
//        Connection connection = factory.newConnection();
       
        // 创建一个频道
        Channel channel = connection.createChannel();
        
        /*
         * 声明一个队列 -- 在RabbitMQ中，队列声明是幂等性的（一个幂等操作的特点是其任意多次执行所产生的影响均与一次执行的影响相同），
         * 也就是说，如果不存在，就创建，如果存在，不会对已经存在的队列产生任何影响。
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println("C [*] Waiting for messages. To exit press CTRL+C");
        // DefaultConsumer类实现了Consumer接口，通过传入一个频道，告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                    AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("C [x] Received '" + message + "'");
            }
        };
        // 自动回复队列应答 -- RabbitMQ中的消息确认机制，后面章节会详细讲解
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}
