package com.cpsh.redis.cluster;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import com.cpsh.util.Log4jXMLConfig;

public class RedisClusterTest {
    private static Logger logger_xml ; 
    static{
        Log4jXMLConfig.initia();//加载log4j.xml文件配置
        logger_xml = LoggerFactory.getLogger(RedisClusterTest.class);
    }
    
	public static void main(String[] args) {
		
		// 数据库链接池配置
		JedisPoolConfig config = new JedisPoolConfig();  
        config.setMaxTotal(100);  
        config.setMaxIdle(50);  
        config.setMinIdle(20);  
        config.setMaxWaitMillis(6 * 1000);  
        config.setTestOnBorrow(true);  
		
		// Redis集群的节点集合
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort("192.168.1.131", 6379));
		jedisClusterNodes.add(new HostAndPort("192.168.1.131", 6380));
		jedisClusterNodes.add(new HostAndPort("192.168.1.136", 6379));
		jedisClusterNodes.add(new HostAndPort("192.168.1.136", 6380));
		jedisClusterNodes.add(new HostAndPort("192.168.1.137", 6379));
		jedisClusterNodes.add(new HostAndPort("192.168.1.137", 6380));
		
		// 根据节点集创集群链接对象
		//JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
		// 集群各节点集合，超时时间，最多重定向次数，链接池
		JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, 2000, 100, config);
		int num = 1000;
		String key = "wusc";
		String value = "";
		for (int i=1; i <= num; i++){
			// 存数据
			jedisCluster.set(key+i, "WuShuicheng"+i); 
			// 取数据
			value = jedisCluster.get(key+i); 
			logger_xml.info(key+i + "=" + value);
			// 删除数据
			//jedisCluster.del(key+i); 
			//value = jedisCluster.get(key+i); 
			//log.info(key+i + "=" + value);
		}
	}
}
