package heapStark.blogCode.lambda.fuctionalnterface;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/3.
 */
@FunctionalInterface
public interface Predicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T t);
}