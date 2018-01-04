package heapStark.blogCode.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Set;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/4.
 */

public class RedisCache {


    private Jedis getJedis() {
        Jedis jedis = null;
        try {
            jedis = JedisClientPool.getPool().getResource();
        } catch (Exception e) {
            //logger.error("getJedis - error, Please Check Jedis Config Settings！", e);
        }
        return jedis;
    }

    /**
     * 删除锁
     *
     * @param key
     * @return
     */
    public Long delateKey(String key) {
        return (Long) redisGo(jedis -> jedis.del(key));
    }
    /**
     * 查询到期时间
     *
     * @param key
     * @return
     */
    public Long pttlKey(String key) {
        return (Long) redisGo(jedis -> jedis.pttl(key));
    }

    /**
     * 锁
     *
     * @param key
     * @return
     */
    public boolean getLock(String key) {

        return (boolean) redisGo(jedis -> {
            Transaction tx = jedis.multi();
            tx.setnx(key, key);
            tx.expire(key, 60);
            List<Object> results = tx.exec();
            System.out.println(results);
            return false;
        });
    }


    /**
     * redis 查询
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        /*如果在外面传入jedis则可以省略参数
        Jedis jedis = getJedis();
        return (String) doEverything(jedisSignal ->  jedis.get(key));*/
        return (String) redisGo(jediss -> jediss.get(key));
    }

    /**
     * 设置过期时间
     *
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(String key, int seconds) {

        return (Long) redisGo(a -> a.expire(key, seconds));
    }

    /**
     * 返回集合的所有值
     *
     * @param key
     * @return
     */
    public Set<String> sMermerbers(String key) {

        return (Set<String>) redisGo(a -> a.smembers(key));
    }


    /**
     * @param redisFunc
     * @return
     */
    private Object redisGo(RedisFunc redisFunc) {
        Jedis jedis = getJedis();
        Object result = null;
        boolean broken = false;
        try {
            result = redisFunc.getValue(jedis);
        } catch (Exception e) {
            broken = true;
        } finally {
            if (broken) {
                JedisClientPool.getPool().returnBrokenResource(jedis);
            } else if (jedis != null) {
                JedisClientPool.getPool().returnResource(jedis);
            }
        }
        return result;
    }
}
