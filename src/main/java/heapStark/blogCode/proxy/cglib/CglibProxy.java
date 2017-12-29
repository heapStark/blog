package heapStark.blogCode.proxy.cglib;

import heapStark.blogCode.proxy.interfaces.impl.SayHelloImpl;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by wangzhilei3 on 2017/12/29.
 */
public class CglibProxy implements MethodInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(CglibProxy.class);
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before");
        //说这个方法可以用于 相同类中的其他对象的方法执行，也就是说这个方法中的obj需要传入相同一个类的另一个对象，否则会进入无限递归循环。
        return methodProxy.invoke(new SayHelloImpl(),objects);
        //        return methodProxy.invokeSuper(o,objects);
    }


}
