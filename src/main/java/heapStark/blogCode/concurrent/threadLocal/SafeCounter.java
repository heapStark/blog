package heapStark.blogCode.concurrent.threadLocal;

/**
 * Created by on 2017/12/27.
 */
public class SafeCounter {
    private ThreadLocal<Integer> threadLocal;

    public SafeCounter() {
        this(0);
    }
    public SafeCounter(Integer begain) {
        //重写initialValue()方法，第一次调用get()方法是调用，需要注意get()方法必须要在set()之前调用
        this.threadLocal = ThreadLocal.withInitial(() -> begain - 1);
    }

    /**
     * 线程安全的计数方法
     *
     * @return
     */
    public int counter() {
        threadLocal.set(threadLocal.get() + 1);
        return threadLocal.get();
        /*另一种实现，不需要重写initialValue()方法
        if (threadLocal.get()==null){
            threadLocal.set(0);
            return 0;
        }else {
            threadLocal.set(threadLocal.get() + 1);
            return threadLocal.get();
        }*/
    }
}
