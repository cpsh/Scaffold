package com.cpsh.activeMQ.example2;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.cpsh.util.DateUtil;

/**
 * ProducerTool.java用于发送消息：
 * @author Administrator
 *
 */
public class ProducerTool {
private String user = ActiveMQConnection.DEFAULT_USER;     
    
    private String password = ActiveMQConnection.DEFAULT_PASSWORD;     
    
    private String url = ActiveMQConnection.DEFAULT_BROKER_URL;     
    
    private String subject = "example2";     
    
    private Destination destination = null;     
    
    private Connection connection = null;     
    
    private Session session = null;     
    
    private MessageProducer producer = null;     
    
    // 初始化     
    private void initialize() throws JMSException, Exception {     
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(     
                user, password, url);     
        connection = connectionFactory.createConnection();     
        /* 
         *    创建session：第一个参数表示是否采用事务消息，
         *    true表示是，消息提交由commit处理，回滚由rollback处理；
         *    false表示不是：
         *      1.Session.AUTO_ACKNOWLEDGE    : session会自动确认接收到的消息
         *      2.Session.CLIENT_ACKNOWLEDGE  : 由客户端程序通过消息的确认方法来确认所收到的消息
         *      3.Session.DUPS_OK_ACKNOWLEDGE : session懒惰，不会立即确认消息，可能导致消息重复投递。
         */
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);     
        //destination = session.createQueue(subject);
        destination = session.createTopic(subject); 
        producer = session.createProducer(destination);     
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);     
    }     
    
    // 发送消息     
    public void produceMessage(String message) throws JMSException, Exception {     
        initialize();     
        TextMessage msg = session.createTextMessage(message);    
        connection.start();     
        System.out.println("Producer:->Sending message: " + message +" , time : "+DateUtil.getFormattedDateString(new Date(),"yyyy-MM-dd HH:mm:ss SSS"));     
        producer.send(msg);     
        System.out.println("Producer:->Message sent complete!");     
    }     
    
    // 关闭连接     
    public void close() throws JMSException {     
        System.out.println("Producer:->Closing connection");     
        if (producer != null)     
            producer.close();     
        if (session != null)     
            session.close();     
        if (connection != null)     
            connection.close();     
    }    
}
