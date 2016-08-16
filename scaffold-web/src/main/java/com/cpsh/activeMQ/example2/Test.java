package com.cpsh.activeMQ.example2;

import javax.jms.JMSException;

import org.apache.activemq.ActiveMQConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cpsh.util.Log4jXMLConfig;
import com.cpsh.util.LoggerUtil;
    
public class Test {   
    /*
     * 通过获取LoggerName方式,获取的Logger对象只会输出该LoggerName对应级别所配置的所有级别日志
     * org.slf4j.Logger    --> Logger logger =loggerFactory.getLogger(LoggerName)
     * commons.logging.Log --> Log logger = LogFactory.getLog(LoggerName)
     * 
     * 通过xml中配置LoggerName为class对应的包名，可以将各个包中的类日志输出到不同的日志文件中
     * LoggerFactory.getLogger(Test.class)
     */
    
//    private static Log logger = LogFactory.getLog("warn_logger");
  //  Log logger = LogFactory.getLog("debug_logger");
    
    private static Logger logger ; 
    static{
        Log4jXMLConfig.initia();//加载log4j.xml文件配置
        logger = LoggerFactory.getLogger(Test.class);
    }
    public static void main(String[] args) throws JMSException, Exception {     
       logger.error(".xml:开始测试ActiveMQ消息队列.......................");
       logger.warn(".xml:开始测试ActiveMQ消息队列.......................");
       logger.info(".xml:开始测试ActiveMQ消息队列.......................");
       logger.debug(".xml:开始测试ActiveMQ消息队列.......................");
       
       LoggerUtil.info(".properties:开始测试ActiveMQ消息队列.......................");
       LoggerUtil.debug(".properties:开始测试ActiveMQ消息队列.......................");
       LoggerUtil.error(".properties:开始测试ActiveMQ消息队列.......................");
       LoggerUtil.warn(".properties:开始测试ActiveMQ消息队列.......................");
       
       
     // TODO Auto-generated method stub     
        ConsumerTool consumer = new ConsumerTool();   
        ConsumerTool2 consumer2 = new ConsumerTool2();
        
        ProducerTool producer = new ProducerTool();    
        System.out.println(ActiveMQConnection.DEFAULT_BROKER_URL+"------------");  
        // 开始监听     
        consumer.consumeMessage();
        consumer2.consumeMessage();
             
        
        
        // 延时500毫秒之后发送消息     
        Thread.sleep(500);     
        producer.produceMessage("Hello, world!");  
        producer.produceMessage("this is my ActiveMQ example!");
        producer.close();  
        
        System.out.println();
        
        
//        consumer.consumeMessage(); //手动接受消息 Message message = consumer.receive();
             
        // 延时500毫秒之后停止接受消息     
        Thread.sleep(500);     
        consumer.close();
        
        System.out.println();
        
        Thread.sleep(500);     
        consumer2.close(); 
    }     
}   