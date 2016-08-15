package com.cpsh.rabbitMQ.OneProviderOneConsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Provider {

    private final static String QUEUE_NAME = "helloword_queue";
    private final static String MQ_HOST = "192.168.230.131";
    private final static int MQ_PORT = 5672;

    public static void main(String[] args) throws Exception {

        // 创建连接工厂 -- 单个地址
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ地址
        factory.setHost(MQ_HOST);
        factory.setPort(MQ_PORT);
/*
        // 设置心跳时间
        factory.setRequestedHeartbeat(5);
        // 网络有问题时，好后可自动恢复设置。
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
        String message = "Hello World!";

        // 发送消息到队列中
        /*
         * rabbitMQ选择了一个空""字符串的默认交换器第一个参数就是交换器的名称。如果输入""空字符串，表示使用默认的匿名交换器。
         * 第二个参数是【routingKey】路由线索 匿名交换器规则： 发送到routingKey名称对应的队列。
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println("P [x] Sent '" + message + "'");
        // 关闭频道和连接
        channel.close();
        connection.close();

    }

}
