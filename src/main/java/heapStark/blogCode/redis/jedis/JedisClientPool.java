package heapStark.blogCode.redis.jedis;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/3.
 */
public class JedisClientPool {

    private static class SingletonHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static JedisPoolConfig config = new JedisPoolConfig();

        static {
            config.setMaxTotal(2);
        }

        private static JedisPool jedisPool = new JedisPool(config,
                "192.168.107.53", 6379, 5000, "workorder");
    }
    //获取连接池
    public static JedisPool getPool() {
        return SingletonHolder.jedisPool;
    }


}
