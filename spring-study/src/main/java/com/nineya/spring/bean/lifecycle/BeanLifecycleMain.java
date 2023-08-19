package com.nineya.spring.bean.lifecycle;import com.nineya.spring.bean.lifecycle.entity.BeanProcess;import org.springframework.beans.factory.config.BeanDefinition;import org.springframework.beans.factory.support.DefaultListableBeanFactory;import org.springframework.context.annotation.AnnotationConfigApplicationContext;public class BeanLifecycleMain {    public static void main(String[] args) {        System.out.println("## 创建容器");        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();        System.out.println("## 扫描Bean注解");        context.scan(BeanLifecycleMain.class.getPackage().getName());        context.refresh();        System.out.println("## 通过class取得bean");        BeanProcess beanProcess = context.getBean(BeanProcess.class);        System.out.println(beanProcess + " : " + beanProcess.hashCode());        System.out.println("## 通过bean名称取得bean");        beanProcess = (BeanProcess) context.getBean("test");        System.out.println(beanProcess + " : " + beanProcess.hashCode());        System.out.println("## 销毁Bean");        ((DefaultListableBeanFactory)context.getBeanFactory()).destroySingleton("test");        System.out.println("## 取得beanDefinition");        BeanDefinition beanDefinition = context.getBeanFactory().getBeanDefinition("test");        System.out.println("beanDefinition : " + beanDefinition);        System.out.println("## 通过bean名称取得bean");        beanProcess = (BeanProcess) context.getBean("test");        System.out.println(beanProcess + " : " + beanProcess.hashCode());    }}