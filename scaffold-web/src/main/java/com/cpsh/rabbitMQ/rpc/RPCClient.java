package com.cpsh.rabbitMQ.rpc;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;

import java.util.UUID;

public class RPCClient {
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;
    private QueueingConsumer consumer;

    private final static String MQ_HOST = "192.168.230.131";
    private final static int MQ_PORT = 5672;

    public RPCClient() throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 设置RabbitMQ地址
        factory.setHost(MQ_HOST);
        factory.setPort(MQ_PORT);
        connection = factory.newConnection();
        channel = connection.createChannel();
        
        //建立一个连接和通道，并声明一个专用的“回调”队列
        replyQueueName = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);
        
        //设置回调队列中的名称
        channel.basicConsume(replyQueueName, true, consumer);
    }

    public String call(String message) throws Exception {
        String response = null;
        String corrId = UUID.randomUUID().toString();

        //设置回调队列中的唯一编号
        BasicProperties props = new BasicProperties.Builder()
                .correlationId(corrId).replyTo(replyQueueName).build();
        
        channel.basicPublish("", requestQueueName, props,
                message.getBytes("UTF-8"));

        /*
         * then code to read a response message from the callback_queue ... 
         * 
         * 循环监听回调队列中的每一个消息
         */
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            //找到与刚才发送任务消息编号相同的消息
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response = new String(delivery.getBody(), "UTF-8");
                break;
            }
        }

        return response;
    }

    public void close() throws Exception {
        connection.close();
    }

    public static void main(String[] argv) {
        RPCClient fibonacciRpc = null;
        String response = null;
        try {
            fibonacciRpc = new RPCClient();

            System.out.println("RPCClient [x] Requesting fib(30)");
            response = fibonacciRpc.call("30");
            System.out.println("RPCClient [.] Got '" + response + "'");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fibonacciRpc != null) {
                try {
                    fibonacciRpc.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}
