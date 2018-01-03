package heapStark.blogCode.collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.Hashtable;

/**
 * blogcode
 * Created by wangzhilei3 on 2018/1/3.
 */
public class MapTest {
    @Test
    public void hashTableTest() {
        Hashtable<Integer,Integer> hashtable = new Hashtable();
        hashtable.put(1,null);
        hashtable.put(null,1);
    }
    @Test
    public void hashMapTest() {
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        hashMap.put(1,null);
        hashMap.put(null,1);
    }
}
