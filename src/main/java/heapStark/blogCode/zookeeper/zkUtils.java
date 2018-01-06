package heapStark.blogCode.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/6.
 */
public class zkUtils {
    private static  RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    private static  CuratorFramework client =
            CuratorFrameworkFactory.newClient(
                    "192.168.107.53:2181",
                    5000,
                    3000,
                    retryPolicy);
    static {
        client.start();
    }
    /**
     * 返回客户端
     * @return
     */
    public static  CuratorFramework getClient(){
        return client;
    }
}
