package heapStark.blogCode.designPattern.builder;

public class Bean extends BaseBean {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "id='" + id + '\'' +
                "} " + super.toString();
    }

    public static class BeanBuilder extends BaseBean.BaseBuilder<BeanBuilder,Bean>{

        public BeanBuilder(Bean bean) {
            super(bean);
        }
        public BaseBuilder setId(String id) {
            this.bean.setId(id);
            return this;
        }
    }
}
