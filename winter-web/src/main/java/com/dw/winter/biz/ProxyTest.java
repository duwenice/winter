package com.dw.winter.biz;

import java.lang.reflect.Proxy;

/**
 * @author duwen
 * @date 2020/9/16 15:40:18
 */
public class ProxyTest implements Test {

    @Override
    public void say() {
        System.out.println("say");
    }

    public static void main(String[] args) {
        ProxyTest proxyTest = new ProxyTest();
        AnotherTest anotherTest = new AnotherTest();
        Test o = (Test)Proxy.newProxyInstance(AnotherTest.class.getClassLoader(), AnotherTest.class.getInterfaces(), new JdkDynamicProxy(anotherTest));
        o.say();
    }
}
