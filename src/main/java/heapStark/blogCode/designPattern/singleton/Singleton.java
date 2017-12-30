package heapStark.blogCode.designPattern.singleton;

import java.util.concurrent.TimeUnit;

/**
 * blogcode
 * Created by wangzhilei3 on 2017/12/30.
 */
public class Singleton {
    private Singleton() {
    }

    private volatile static Singleton instance = null;
    //private static Singleton instance = new Singleton();//懒汉式

    //双重检查锁
    public static Singleton getInstance() {
        //先检查实例是否存在，如果不存在才进入下面的同步块
        if (instance == null) {
            //同步块，线程安全的创建实例
            synchronized (Singleton.class) {
                //再次检查实例是否存在，如果不存在才真正的创建实例
                if (instance == null) {
                    instance = new Singleton();
                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    /*//懒汉式懒汉式是典型的时间换空间,就是每次获取实例都会进行判断，看是否需要创建实例，浪费判断的时间。当然，如果一直没有人使用的话，那就不会创建实例，则节约内存空间
　　  //由于懒汉式的实现是线程安全的，这样会降低整个访问的速度，而且每次都要判断。
    public static synchronized Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }*/
    private static class SingletonHolder{
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static Singleton instance = new Singleton();
    }
    /*//静态内部类
    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }*/

}
