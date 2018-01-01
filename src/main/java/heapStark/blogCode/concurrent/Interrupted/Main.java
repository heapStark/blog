package heapStark.blogCode.concurrent.Interrupted;

import org.junit.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/1.
 */
public class Main {
    @Test
    public void interruptedTest() {

        Runnable runnableError = () -> {
        //不会退出
            while (!Thread.interrupted()) {
                System.out.println(new Date());
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //unreachable
            System.out.println("interrupted: " + Thread.interrupted());
        };
        //可以退出
        Runnable runnable = () -> {
            try {
                while (!Thread.interrupted()) {
                    System.out.println(new Date());
                    TimeUnit.SECONDS.sleep(3);
                }
                //unreachable
                System.out.println("interrupted: " + Thread.interrupted());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("reachable");
        };
        Thread thread = new Thread(runnable);

        thread.start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        try {
            thread.join();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assert (!thread.isInterrupted());
        System.out.println("end: " + new Date());
    }
}
