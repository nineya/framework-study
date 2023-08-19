package com.nineya.spring.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

interface CglibTargetInterface {
    String test(String str);
}

class CglibTargetObject {

    public String test(String str) {
        System.out.println("被代理函数执行：" + str);
        return "被代理函数的返回结果";
    }
}

/**
 * cglib代理处理类
 */
class CglibProxyHandler implements MethodInterceptor {
    private Object target;

    public CglibProxyHandler() {
    }

    public CglibProxyHandler(Object target){
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("代理函数执行：" + method);
        if (target == null) {
            return "代理函数的返回结果";
        }
        return methodProxy.invoke(target, objects);
    }
}

public class CglibProxyMain {
    private static void proxyObject() {
        // 被代理对象
        CglibTargetObject targetObject = new CglibTargetObject();

        Enhancer enhancer = new Enhancer();
        // 被代理的类
        enhancer.setSuperclass(CglibTargetObject.class);
        // cglib代理处理类
        enhancer.setCallback(new CglibProxyHandler(targetObject));
        // 取得代理后的对象
        CglibTargetObject proxyTargetObject = (CglibTargetObject) enhancer.create();

        String result = proxyTargetObject.test("这是请求参数");
        System.out.println("执行结果：" + result);
    }
    
    private static void proxyInterface() {
        Enhancer enhancer = new Enhancer();
        // 被代理的类
        enhancer.setSuperclass(CglibTargetInterface.class);
        // cglib代理处理类
        enhancer.setCallback(new CglibProxyHandler());
        // 取得代理后的对象
        CglibTargetInterface proxyTargetObject = (CglibTargetInterface) enhancer.create();

        String result = proxyTargetObject.test("这是请求参数");
        System.out.println("执行结果：" + result);
    }
    
    public static void main(String[] args) {
        System.out.println("## CGLIB 代理接口");
        proxyInterface();
        System.out.println("## CGLIB 代理对象");
        proxyObject();
    }
}