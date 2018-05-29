package heapStark.blogCode.designPattern.builder;

public class BaseBean {
    private  int age;
    private  String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public static class BaseBuilder<T extends BaseBuilder,P extends BaseBean>{
        protected P bean;

        public BaseBuilder(P bean) {
            this.bean = bean;
        }
        public int getAge() {
            return bean.getAge();
        }

        public T setAge(int age) {
            this.bean.setAge(age);
            return (T)this;
        }

        public String getName() {
            return bean.getName();
        }

        public T setName(String name) {
            this.bean.setName(name);
            return (T)this;
        }

        public P builder(){
            return bean;
        }
    }
}
