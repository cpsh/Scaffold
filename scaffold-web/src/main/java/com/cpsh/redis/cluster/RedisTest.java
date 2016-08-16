package com.cpsh.redis.cluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.cpsh.util.Log4jXMLConfig;

public class RedisTest {
    private static Logger logger_xml ; 
    static{
        Log4jXMLConfig.initia();//加载log4j.xml文件配置
        logger_xml = LoggerFactory.getLogger(RedisTest.class);
    }
	@SuppressWarnings("resource")
    public static void main(String[] args) {
		
		Jedis jedis = new Jedis("192.168.1.131", 6379); // 不支持集群 MOVED
		String key = "wusc2";
		String value = jedis.get(key); // 取数据
		logger_xml.info(key + "=" + value);

	}
}
