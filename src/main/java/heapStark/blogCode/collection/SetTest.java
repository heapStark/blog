package heapStark.blogCode.collection;

import org.junit.Test;

import java.util.HashSet;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/3.
 */
public class SetTest {
    @Test
    public void hashMapTest() {
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(null);
        assert (hashSet.contains(null));
    }
}
