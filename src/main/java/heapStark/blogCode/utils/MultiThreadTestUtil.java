package heapStark.blogCode.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangzhilei3 on 2017/12/27.
 */
public class MultiThreadTestUtil {
    private static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void multiThreadRun(Runnable runnable, int runnables) throws Exception {
        for (int j = 0; j < runnables; j++) {
            executorService.execute(runnable);
        }
        //TimeUnit.SECONDS.sleep(5);
    }

    public static void multiThreadRun(Runnable runnable) throws Exception {
        multiThreadRun(runnable, 5);
    }
}
