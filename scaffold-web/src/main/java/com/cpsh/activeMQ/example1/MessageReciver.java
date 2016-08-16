package com.cpsh.activeMQ.example1;

import javax.jms.Destination;
import javax.jms.TextMessage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
/**
 * 消息接收方
 */
public class MessageReciver{
    /*@Autowired
    private static JmsTemplate jmsTemplate ;

    @Autowired
    private static Destination destination;*/
    
    public static void main(String args[]) throws Exception {
        ApplicationContext context=new FileSystemXmlApplicationContext("/WebRoot/WEB-INF/applicationContext.xml"); 
        JmsTemplate jmsTemplate = (JmsTemplate) context.getBean("jmsTemplate");
        Destination destination = (Destination) context.getBean("Queue1");
 
        TextMessage msg = null;
        //是否继续接收消息
        boolean isContinue = true;
        while (isContinue) {
            msg = (TextMessage) jmsTemplate.receive(destination);
            System.out.println("收到消息 :" + msg.getText());
            if (msg.getText().equals("end")) {
                isContinue = false;
                System.out.println("收到退出消息，程序要退出！");
            }
        }
        System.out.println("程序退出了！");
    }
}