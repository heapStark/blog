package heapStark.blogCode.redis.redisson;

import org.junit.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/4.
 */
public class RedissonTest {
    @Test
    public void redissonTest(){
        RedisUtils redisUtils = RedisUtils.getInstance();
        //"192.168.107.53", 6379, 5000, "workorder"
        Config config = new Config();
        config.useSingleServer().setAddress("192.168.107.53:6379").setPassword("workorder");
        RedissonClient redissonClient = redisUtils.getRedisson(config);
        RLock rLock = redissonClient.getLock("hello");
    }
}
