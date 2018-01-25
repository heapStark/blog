package heapStark.blogCode.proxy.jdkProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wangzhilei3 on 2017/12/29.
 */
public class JDKProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method.invoke(proxy,args); proxy实际没用
        //method.invoke(this, args);
        System.out.println("hello");
        return "hello";
    }
}
