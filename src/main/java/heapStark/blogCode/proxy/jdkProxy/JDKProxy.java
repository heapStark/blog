package heapStark.blogCode.proxy.jdkProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wangzhilei3 on 2017/12/29.
 */
public class JDKProxy implements InvocationHandler {
    private final static Logger logger = LoggerFactory.getLogger(JDKProxy.class);


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("hello");
        return "hello";
    }
}
