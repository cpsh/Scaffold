package com.cpsh.redis.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import redis.clients.jedis.JedisCluster;

import com.cpsh.util.Log4jXMLConfig;

public class RedisClusterSpringTest {
    private static Logger logger_xml ; 
    static{
        Log4jXMLConfig.initia();//加载log4j.xml文件配置
        logger_xml = LoggerFactory.getLogger(RedisClusterSpringTest.class);
    }
    
	public static void main(String[] args) {
		try {
//			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			AbstractApplicationContext context = new FileSystemXmlApplicationContext("/WebRoot/WEB-INF/applicationContext.xml");
//			context.start();
			
			JedisCluster jedisCluster = (JedisCluster) context.getBean("jedisCluster");
			int num = 1000;
			String key = "wusc";
			String value = "";
			for (int i=1; i <= num; i++){
				// 存数据
				//jedisCluster.set(key+i, "WuShuicheng"+i);
				//jedisCluster.setex(key+i, 60, "WuShuicheng"+i); // 设置有效时间
				
				// 取数据
				value = jedisCluster.get(key+i); 
				logger_xml.info(key+i + "=" + value);
				
				// 删除数据
				//jedisCluster.del(key+i); 
				//value = jedisCluster.get(key+i); 
				//log.info(key+i + "=" + value);
			}

//			context.stop();
		} catch (Exception e) {
		    logger_xml.error("==>RedisSpringTest context start error:", e);
			System.exit(0);
		} finally {
		    logger_xml.info("===>System.exit");
			System.exit(0);
		}
	}
}
