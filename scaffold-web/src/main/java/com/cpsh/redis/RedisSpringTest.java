package com.cpsh.redis;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import com.scaffold.common.util.Log4jXMLConfig;
import com.scaffold.common.util.LoggerUtil;

public class RedisSpringTest {
    private static Logger logger_xml ; 
    static{
        Log4jXMLConfig.initia();//加载log4j.xml文件配置
        logger_xml = LoggerFactory.getLogger(RedisSpringTest.class);
    }

	@SuppressWarnings("resource")
    public static void main(String[] args) {
		try {
//			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
			AbstractApplicationContext context = new FileSystemXmlApplicationContext("/WebRoot/WEB-INF/applicationContext.xml");
//			context.start();
			
			ShardedJedisPool pool = (ShardedJedisPool) context.getBean("shardedJedisPool");
			ShardedJedis jedis = pool.getResource();
			
			String key = "wusc";
			String value = "";
			
			jedis.del(key); // 删数据
			
			jedis.set(key, "WuShuicheng"); // 存数据
			value = jedis.get(key); // 取数据
			logger_xml.info(key + "=" + value);
			LoggerUtil.info(key + "=" + value);//log4j.properties生效
			
			jedis.set(key, "WuShuicheng2"); // 存数据
			value = jedis.get(key); // 取数据
			logger_xml.info(key + "=" + value);
			LoggerUtil.info(key + "=" + value);//log4j.properties生效
			
			jedis.del(key); // 删数据
			value = jedis.get(key); // 取数据
			logger_xml.info(key + "=" + value);
			LoggerUtil.info(key + "=" + value);//log4j.properties生效
			
			
//			context.stop();
		} catch (Exception e) {
		    logger_xml.error("==>RedisSpringTest context start error:", e);
		    LoggerUtil.error("==>RedisSpringTest context start error:");
		    
			System.exit(0);
		} finally {
		    logger_xml.info("===>System.exit");
		    LoggerUtil.info("===>System.exit");
			System.exit(0);
		}
	}
}
