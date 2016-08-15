package com.cpsh.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.cpsh.util.Log4jXMLConfig;
import com.cpsh.util.LoggerUtil;

public class RedisTest {

    private static Logger logger_xml ; 
    static{
        Log4jXMLConfig.initia();//加载log4j.xml文件配置
        logger_xml = LoggerFactory.getLogger(RedisTest.class);
    }
       
	@SuppressWarnings("resource")
    public static void main(String[] args) {
		
		Jedis jedis = new Jedis("192.168.230.131",6379);
		
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
		
		//jedis.del(key); // 删数据
		//value = jedis.get(key); // 取数据
		//log.info(key + "=" + value);
	}
}
