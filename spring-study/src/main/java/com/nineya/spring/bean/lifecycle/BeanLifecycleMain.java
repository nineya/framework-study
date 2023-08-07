package com.nineya.spring.bean.lifecycle;import com.nineya.spring.bean.lifecycle.entity.BeanProcess;import org.springframework.beans.factory.config.BeanDefinition;import org.springframework.beans.factory.support.DefaultListableBeanFactory;import org.springframework.context.annotation.AnnotationConfigApplicationContext;public class BeanLifecycleMain {    public static void main(String[] args) {        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();        // 配置注解扫描路径        context.scan(BeanLifecycleMain.class.getPackage().getName());        context.refresh();        // 通过class取得bean        BeanProcess beanProcess = context.getBean(BeanProcess.class);        System.out.println(beanProcess + " : " + beanProcess.hashCode());        // 通过bean名称取得bean        beanProcess = (BeanProcess) context.getBean("test");        System.out.println(beanProcess + " : " + beanProcess.hashCode());        // 销毁bean        ((DefaultListableBeanFactory)context.getBeanFactory()).destroySingleton("test");        // 取得beanDefinition        BeanDefinition beanDefinition = context.getBeanFactory().getBeanDefinition("test");        System.out.println("beanDefinition : " + beanDefinition);        // 通过bean名称取得bean        beanProcess = (BeanProcess) context.getBean("test");        System.out.println(beanProcess + " : " + beanProcess.hashCode());    }}