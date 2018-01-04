package heapStark.blogCode.redis.jedis;

import redis.clients.jedis.Jedis;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/4.
 */
@FunctionalInterface
public interface RedisFunc {
    Object getValue(Jedis jedis) throws Exception;
}