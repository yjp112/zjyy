package com.supconit.montrol.util;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * RedisUtil
 *
 * @author 猪仔空空
 * @date 2015-8-21
 */
public class RedisUtil {
    private static JedisPool pool;
    public static JedisPool getPool( String redisIP){
        if(pool == null){
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
            config.setMaxActive(500);
            //控制一个pool最多有多少个状态为idle（空闲）的jedis实例
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWait(10000);
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, redisIP);
        }
        return pool;
    }
    
    public static JedisPool getPool( String redisIP,int port){
        if(pool == null){
            JedisPoolConfig config = new JedisPoolConfig();
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)
            config.setMaxActive(500);
            //控制一个pool最多有多少个状态为idle（空闲）的jedis实例
            config.setMaxIdle(5);
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
            config.setMaxWait(10000);
            config.setTestOnBorrow(true);
            pool = new JedisPool(config, redisIP,port);
        }
        return pool;
    }
}
