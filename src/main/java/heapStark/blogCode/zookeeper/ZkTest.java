package heapStark.blogCode.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/6.
 */
public class ZkTest {
    static CuratorFramework curatorFramework = zkUtils.getClient();

    @Test
    public void zkLockTest() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, "/blog/test");
        lock.acquire(100, TimeUnit.SECONDS);
        assert (lock.acquire(10, TimeUnit.SECONDS));
        lock.release();
    }
    @Test
    public void zkPathTest() throws Exception {
        curatorFramework.create().forPath("/path");
        curatorFramework.setData().forPath("/path","test".getBytes());
        String s = new String(curatorFramework.getData().forPath("/path"));
        curatorFramework.delete().forPath("/path");
        assert (s.equals("test"));
    }
}
