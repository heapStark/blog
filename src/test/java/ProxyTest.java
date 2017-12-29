import heapStark.blogCode.proxy.cglib.CglibProxy;
import heapStark.blogCode.proxy.interfaces.impl.SayHelloImpl;
import net.sf.cglib.proxy.Enhancer;
import org.junit.Test;


/**
 * Created by wangzhilei3 on 2017/12/29.
 */
public class ProxyTest {
    @Test
    public void CglibTest() {
        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(SayHelloImpl.class);
        enhancer.setCallback(new CglibProxy());
        SayHelloImpl s = (SayHelloImpl) enhancer.create();
        s.say();
        String returnHello = s.returnHello();
        assert (returnHello=="hello");
    }
}
