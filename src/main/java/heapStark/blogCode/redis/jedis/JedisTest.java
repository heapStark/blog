package heapStark.blogCode.redis.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/3.
 */
public class JedisTest {

    private JedisPool jedisPool = JedisClientPool.getPool();

    private Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jedis;
    }
    @Test
    public void jedis (){
        Jedis jedis = getJedis();

    }
}
