package com.cpsh.util;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

public class SpyMemcachedClient implements DisposableBean{

    
    private MemcachedClient memcachedClient;
    
    private long shutdownTimeout = 1000;
    
    /**
     * 初始化信息
     */
    public void initialize() {
        LoggerUtil.debug("SpyMemcachedClient初始化链接远程IP机器---------------------------------------");
    }
    
    /**
     * Get方法, 转换结果类型并屏蔽异常, 仅返回Null.
     */
    public <T> T get(String key) {
        try {
            if(key!=null) key=StringUtils.replace(key, " ", "#");
            return (T) memcachedClient.get(key);
        } catch (RuntimeException e) {
            handleException(e, key);
            return null;
        }
    }
    /**
     * GetBulk方法, 转换结果类型并屏蔽异常.
     */
    public <T> Map<String, T> getBulk(Collection<String> keys) {
        try {
            return (Map<String, T>) memcachedClient.getBulk(keys);
        } catch (RuntimeException e) {
            handleException(e, StringUtils.join(keys, ","));
            return null;
        }
    }

    /**
     * 异步Set方法, 不考虑执行结果.
     */
    public void set(String key, int expiredTime, Object value) {
        if(key!=null) key=StringUtils.replace(key, " ", "#");
        memcachedClient.set(key, expiredTime, value);
    }

    /**
     * 安全的Set方法,在20毫秒内返回结果, 否则返回false并取消操作.
     */
    public boolean safeSet(String key, Object value, int expiration) {
        if(key!=null) key=StringUtils.replace(key, " ", "#");
        Future<Boolean> future = memcachedClient.set(key, expiration, value);
        try {
            return future.get(1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    /**
     * 异步 Delete方法, 不考虑执行结果.
     */
    public void delete(String key) {
        if(key!=null) key=StringUtils.replace(key, " ", "#");
        memcachedClient.delete(key);
    }

    /**
     * 安全的Set方法,在1秒内返回结果, 否则返回false并取消操作.
     */
    public boolean safeDelete(String key) {
        if(key!=null) key=StringUtils.replace(key, " ", "#");
        Future<Boolean> future = memcachedClient.delete(key);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    /**
     * Incr方法.
     */
    public long incr(String key, int by, long defaultValue) {
        return memcachedClient.incr(key, by, defaultValue);
    }

    /**
     * Decr方法.
     */
    public long decr(String key, int by, long defaultValue) {
        return memcachedClient.decr(key, by, defaultValue);
    }

    /**
     * 异步Incr方法, 不支持默认值, 若key不存在返回-1.
     */
    public Future<Long> asyncIncr(String key, int by) {
        return memcachedClient.asyncIncr(key, by);
    }

    /**
     * 异步Decr方法, 不支持默认值, 若key不存在返回-1.
     */
    public Future<Long> asyncDecr(String key, int by) {
        return memcachedClient.asyncDecr(key, by);
    }

    private RuntimeException handleException(Exception e, String key) {
        LoggerUtil.warn("spymemcached client receive an exception with key:" + key);
        return new RuntimeException(e);
    }

    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public void setShutdownTimeout(long shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }
    
//    @Override
    public void destroy() throws Exception {
        if (memcachedClient != null) {
            memcachedClient.shutdown(shutdownTimeout, TimeUnit.MILLISECONDS);
        }
    }
}
