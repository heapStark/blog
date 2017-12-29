package heapStark.blogCode.concurrent.callable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangzhilei3 on 2017/12/19.
 */
public class Call implements Callable<List<String>> {
    public List<String> call() throws Exception {
        TimeUnit.SECONDS.sleep(3);
//        int a = 1/0;
        return Arrays.asList("hello","world");
    }
}
