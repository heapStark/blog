package heapStark.blogCode.proxy.interfaces.impl;


import heapStark.blogCode.proxy.interfaces.SayHello;

/**
 * Created by wangzhilei3 on 2017/12/29.
 */
public class SayHelloImpl implements SayHello {
    @Override
    public void say() {
        System.out.println("hello");
    }

    @Override
    public String returnHello() {
        return "hello";
    }
}
