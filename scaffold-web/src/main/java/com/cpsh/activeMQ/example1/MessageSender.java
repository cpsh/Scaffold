package com.cpsh.activeMQ.example1;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

/**
 * 发送消息方 
 */
public class MessageSender extends Thread {
    /*@Autowired
    private static JmsTemplate jmsTemplate ;

    @Autowired
    private static Destination destination;*/
    
    
    @SuppressWarnings("resource")
    public static void main(String args[]) throws Exception {
        //ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
        ApplicationContext context=new FileSystemXmlApplicationContext("/WebRoot/WEB-INF/applicationContext.xml"); 
        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
        Destination Queue1 = (Destination) context.getBean("Queue1");
        for (int i = 1; i < 100; i++) {
            System.out.println("Queue1 发送 i=" + i);
            // 消息产生者
            MyMessageCreator myMessageCreator = new MyMessageCreator();
            myMessageCreator.n = i;
            jmsTemplate.send(Queue1, myMessageCreator);
//            sleep(10000);// 10秒后发送下一条消息
        }
        System.out.println();
        
        Destination Queue2 = (Destination) context.getBean("Queue2");
        for (int i = 1; i < 100; i++) {
            // 消息产生者
            System.out.println("Queue2 发送 i=" + i);
            MyMessageCreator myMessageCreator = new MyMessageCreator();
            myMessageCreator.n = i;
            jmsTemplate.send(Queue2, myMessageCreator);
        }
    }
}