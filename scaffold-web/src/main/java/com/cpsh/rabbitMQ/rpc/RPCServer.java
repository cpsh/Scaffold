package com.cpsh.rabbitMQ.rpc;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class RPCServer {

    private final static String MQ_HOST = "192.168.230.131";
    private final static int MQ_PORT = 5672;

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    /**
     * 计算斐波那契数列简单方法
     * 
     * @param n
     * @return
     */
    private static int fib(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] argv) {
        Connection connection = null;
        Channel channel = null;
        try {

            /*
             * 第一步仍然是建立连接、频道和声明队列
             */

            // 创建连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            // 设置RabbitMQ地址
            factory.setHost(MQ_HOST);
            factory.setPort(MQ_PORT);

            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

            /*
             * 为了达到负载均衡，需要通过channel.basicQos来设置从队列中预取消息的个数 basicConsume 访问队列
             */
            channel.basicQos(1);

            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

            System.out.println("RPCServer [x] Awaiting RPC requests");

            while (true) {
                String response = null;

                QueueingConsumer.Delivery delivery = consumer.nextDelivery();

                BasicProperties props = delivery.getProperties();
                BasicProperties replyProps = new BasicProperties.Builder()
                        .correlationId(props.getCorrelationId()).build();

                try {
                    String message = new String(delivery.getBody(), "UTF-8");
                    int n = Integer.parseInt(message);

                    System.out.println("RPCServer [.] fib(" + message + ")");
                    
                    /* **************************************************************
                     * 
                     * 示例中是简单的调用本地方法，生产中需要进行调用实际的RPC远程服务器方法逻辑，可扩展
                     * 
                     * **************************************************************
                     */
                    
                    response = "" + fib(n);
                } catch (Exception e) {
                    System.out.println(" [.] " + e.toString());
                    response = "";
                } finally {
                    /*
                     * RPC服务器没有将回调Queue绑定到Exchange上，这是因为RPC服务器将应答消息发布到RabbitMQ，
                     * 而没有指定交换器时，RabbitMQ就知道目的地是应答队列，routing_key 就是回调Queue的名称-
                     * ---basicPublish方法第二个参数就是routing_key==props.getReplyTo()===>consumer指定的replyTo为队列名称
                     */

                    channel.basicPublish("", props.getReplyTo(), replyProps,
                            response.getBytes("UTF-8"));

                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(),
                            false);
                }
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
