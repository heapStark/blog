package heapStark.blogCode.lambda;

import heapStark.blogCode.jdk.Vo.Student;
import org.junit.Test;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by wangzhilei3 on 2017/12/29.
 */
public class Main {
    @Test
    public void voidTest() throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println(this.getClass());
            assert (this.getClass().getName() == "heapStark.blogCode.lambda.Main");
        });
        thread.start();
        thread.join();
    }

    @Test
    public void streamTest() throws InterruptedException {

        List<Integer> list = Arrays.asList(1, 2, 3);
        Stream stream = list.stream();
        assert (stream.collect(Collectors.toList()).equals(list));
        Stream streamOf = Stream.of(1, 2, 3);
        assert (!Objects.equals(stream, streamOf));
        Stream streamGe = Stream.generate(() -> 1).limit(3);
        assert (Objects.equals(streamGe.collect(Collectors.toList()), Arrays.asList(1, 1, 1)));
        Stream streamIter = Stream.iterate(1, (a) -> a++).limit(3);
        assert (Objects.equals(streamIter.collect(Collectors.toList()), Arrays.asList(1, 1, 1)));
        String sentence = "Program creek is a Java site.";
        Stream<String> wordStream = Pattern.compile("\\W").splitAsStream(sentence);
        String[] wordArr = wordStream.toArray(String[]::new);
        assert (Arrays.asList(wordArr).equals(Arrays.asList("Program", "creek", "is", "a", "Java", "site")));

        Stream<List<Integer>> stream2 = Stream.of(Arrays.asList(1), Arrays.asList(2));
        List<Integer> list1 = stream2.flatMap(numbers -> numbers.stream()).collect(Collectors.toList());

        //collect()及早求值
        assert (list.stream().collect(Collectors.toList()).equals(list));
        //map() R apply(T t);
        assert (list.stream().map((a) -> a.toString()).collect(Collectors.toList()).equals(Arrays.asList("1", "2", "3")));
        //filter() boolean test(T t);
        assert (list.stream().filter(a -> a >= 2).collect(Collectors.toList()).equals(Arrays.asList(2, 3)));
        //flatMap() R apply(T t);
        Stream<List<Integer>> streamInteger = Stream.of(Arrays.asList(1), Arrays.asList(2));
        assert (streamInteger.collect(Collectors.toList()).equals(Arrays.asList(Arrays.asList(1), Arrays.asList(2))));
        Stream<Integer> streamFlatMap = Stream.of(Arrays.asList(1), Arrays.asList(2)).flatMap(s -> s.stream());
        assert (!streamFlatMap.equals(Stream.of(Arrays.asList(1, 2))));
        assert (streamFlatMap.collect(Collectors.toList()).equals(Arrays.asList(1, 2)));
        //max(),min() boolean test(T t);
        assert (list.stream().max(Comparator.comparing(a -> a)).get() == 3);
        assert (list.stream().min((a, b) -> a - b).get() == 1);
        //reduce
        assert (list.stream().reduce((a, b) -> a + b).get().equals(6));
        //对于延迟求值可以进行链式操作
        /**
         * 基本类型
         */
        Student student = new Student("wang", 1, new Date());
        Student student1 = new Student("wang", 2, new Date());
        Student student2 = new Student("wang", 3, new Date());
        Stream<Student> streamStudent = Stream.of(student, student1, student2);
        IntSummaryStatistics statistics = streamStudent.mapToInt((a) -> a.getAge()).summaryStatistics();
        assert (statistics.getMax() == 3);
        assert (statistics.getMin() == 1);
        assert (statistics.getSum() == 6);
        assert (statistics.getAverage() == 2);

    }

    /**
     * 方法引用
     */
    @Test
    public void streamTest2() throws InterruptedException {
        Stream<String> stream = Stream.of("1", "2");
        assert (stream.map(String::toUpperCase).collect(Collectors.toList()).equals(Arrays.asList("1", "2")));
        assert (Stream.of("1", "2").map(Integer::valueOf).collect(Collectors.toList()).equals(Arrays.asList(1, 2)));

    }

    /**
     * 顺序
     */
    @Test
    public void streamOrderTest() throws InterruptedException {
        Set set = new HashSet();
        set.addAll(Arrays.asList(5, 1, 2, 3, 4));
        //set本身不保证顺序改为，LinkedHashSet则不通过
        assert (!Arrays.asList(5, 1, 2, 3, 4).equals(set.stream().collect(Collectors.toList())));

    }

    /**
     * collect方法，定制收集器
     */
    @Test
    public void streamCollectTest() throws InterruptedException {
        Set<Integer> set = new HashSet();
        set.addAll(Arrays.asList(5, 1, 2, 3, 4));
        //转换为集合
        assert (new ArrayList<>(set).equals(set.stream().collect(Collectors.toCollection(ArrayList::new))));
        //转换为值
        assert (set.stream().collect(Collectors.maxBy(Comparator.comparing(a->a))).get()==5);
        //数据分块(2分)
        /*System.out.println(set.stream().collect(Collectors.partitioningBy(a->a%2==0)));
        System.out.println(set.stream().collect(Collectors.partitioningBy(a->a%2==0)).get(Boolean.TRUE));*/
        assert (set.stream().collect(Collectors.partitioningBy(a->a%2==0)).get(Boolean.TRUE).equals(Arrays.asList(2,4)));

        //数据分组，二分
        assert (set.stream().collect(Collectors.groupingBy(a->a%2==0)).get(Boolean.TRUE).equals(Arrays.asList(2,4)));
        //数据分组，多分
        assert (set.stream().collect(Collectors.groupingBy(a->a)).get(1).equals(Arrays.asList(1)));




    }
}
