package heapStark.blogCode.concurrent.forkJoin;

import java.util.concurrent.RecursiveTask;

/**
 * Created by wangzhilei3 on 2017/12/19.
 */
public class SumTask extends RecursiveTask<Integer> {
    private int start;
    private int end;

    public SumTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        System.out.println(Thread.currentThread().getName());
        if (start==end){
            return start;
        }else if (end-start==1){
            return end+start;
        }else {
            SumTask left = new SumTask(start,(start+end)/2);
            SumTask right = new SumTask((start+end)/2+1,end);
            left.fork();
            right.fork();
            return left.join()+right.join();
        }

    }
}
