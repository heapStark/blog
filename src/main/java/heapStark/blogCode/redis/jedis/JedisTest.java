package heapStark.blogCode.redis.jedis;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/3.
 */
public class JedisTest {
    private final static String KEY = "hello";

    @Test
    public void testLock() {
        RedisCache redisCache = new RedisCache();
        redisCache.delateKey(KEY);
        redisCache.getLock(KEY);
        Long pttl = redisCache.pttlKey(KEY);
        assert (pttl <= 60000);

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        redisCache.getLock(KEY);
        System.out.println(redisCache.pttlKey(KEY));
        Long pttl2 = redisCache.pttlKey(KEY);
        System.out.println(pttl2);
        assert (50000 < pttl2 && pttl2 <= 60000);
        redisCache.delateKey(KEY);
    }
}
