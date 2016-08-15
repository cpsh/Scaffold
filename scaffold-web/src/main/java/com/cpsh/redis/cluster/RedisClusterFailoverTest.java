package com.cpsh.redis.cluster;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import com.cpsh.util.Log4jXMLConfig;

/**
 * 集群扩展，添加/撤销节点，可用性测试
 * @author Administrator
 *
 */
public class RedisClusterFailoverTest {
    private static Logger log ; 
    static{
        Log4jXMLConfig.initia();//加载log4j.xml文件配置
        log = LoggerFactory.getLogger(RedisClusterFailoverTest.class);
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
        
		
		try {
			
			// 根据节点集创集群链接对象
			//JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
			// 集群各节点集合，超时时间，最多重定向次数，链接池
			JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, 2000, 100, config);
			int num = 1000;
			String key = "wusc";
			String value = "";
			int count = 1;

			while(true){
				for (int i=1; i <= num; i++){
					try {
						// 存数据
						//jedisCluster.set(key+i, "WuShuicheng"+i); 
						
						// 取数据
						value = jedisCluster.get(key+i); 
						log.info(key+i + "=" + value);
						if (value == null || "".equals(value)){
							log.error("===>break" + key+i + " value is null");
							break;
						}
					} catch (Exception e) {
						log.error("====>", e);
						Thread.sleep(3000);
						continue;
					}
					// 删除数据
					//jedisCluster.del(key+i); 
					//value = jedisCluster.get(key+i); 
					//log.info(key+i + "=" + value);
				}
				log.info("===================================>count:" + count);
				if (value == null || "".equals(value)){
					break;
				}
				count++;
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			log.error("====>", e);
		}
		
	}
}
