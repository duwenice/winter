package com.dw.winter.biz;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author duwen
 * @date 2020/9/16 14:47:15
 */
@Slf4j
public class JdkDynamicProxy implements InvocationHandler {

    private final Object object;

    public JdkDynamicProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("开始动态代理");
        Object result = method.invoke(object, args);
        log.info("结束动态代理");
        return result;
    }
}
