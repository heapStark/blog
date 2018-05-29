package heapStark.blogCode.designPattern.builder;

public class Main {

    public static void main(String[] args) {
        Bean.BeanBuilder baseBuilder = new Bean.BeanBuilder(new Bean());
        baseBuilder.setId("123").setAge(123).setName("qwer");
        System.out.print(baseBuilder.builder());
    }
}
