package heapStark.blogCode.proxy.jdkProxy;

import heapStark.blogCode.proxy.interfaces.SayHello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wangzhilei3 on 2017/12/29.
 */
public class JDKProxy implements InvocationHandler {
    private final static Logger logger = LoggerFactory.getLogger(JDKProxy.class);

    static  SayHello sayHello =(SayHello) Proxy.newProxyInstance(SayHello.class.getClassLoader(),
            new Class[]{SayHello.class},new JDKProxy());
    public static void main(String ... args){
        sayHello.say();
        String result = sayHello.returnHello();
        logger.info(":{}",result);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("hello");
        return "hello world";
    }
}
