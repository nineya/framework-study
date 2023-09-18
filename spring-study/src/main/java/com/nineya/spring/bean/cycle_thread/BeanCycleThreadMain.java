package com.nineya.spring.bean.cycle_thread;import com.nineya.spring.bean.cycle_thread.entity.OneBean;import org.springframework.context.annotation.AnnotationConfigApplicationContext;public class BeanCycleThreadMain {    public static void main(String[] args) {        System.out.println("## 创建容器");        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();        System.out.println("## 扫描Bean注解");        context.scan(BeanCycleThreadMain.class.getPackage().getName());        context.refresh();        new Thread(() -> {            System.out.println("## Thread: 通过class取得oneBean");            OneBean oneBean = context.getBean(OneBean.class);            System.out.println("Thread: " + oneBean + " : " + oneBean.hashCode());            System.out.println("Thread: getTwoBean : " + oneBean.getTwoBean());        }).start();        new Thread(() -> {            System.out.println("## Thread: 通过class取得oneBean");            synchronized (context.getDefaultListableBeanFactory().getSingletonMutex()) {                OneBean oneBean = context.getBean(OneBean.class);                System.out.println("Thread: " + oneBean + " : " + oneBean.hashCode());                System.out.println("Thread: getTwoBean : " + oneBean.getTwoBean());            }        }).start();        System.out.println("## Main: 通过class取得oneBean");        OneBean oneBean = context.getBean(OneBean.class);        System.out.println("Main: " + oneBean + " : " + oneBean.hashCode());        System.out.println("Main: getTwoBean : " + oneBean.getTwoBean());    }}