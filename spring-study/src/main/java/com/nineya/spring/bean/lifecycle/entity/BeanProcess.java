package com.nineya.spring.bean.lifecycle.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Getter
@Setter
@ToString
public class BeanProcess implements BeanNameAware, InitializingBean, DisposableBean {
    private String name;
    
    @PostConstruct
    public void init() {
        System.out.println("Bean @PostConstruct: name = " + name);
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("Bean @PreDestroy: name = " + name);
    }

    @Override
    public void destroy() {
        System.out.println("Bean DisposableBean: name = " + name);
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("Bean InitializingBean: name = " + name);
    }
    
    public void initMethod() {
        System.out.println("Bean InitMethod: name = " + name);
    }
    
    public void destroyMethod() {
        System.out.println("Bean DestroyMethod: name = " + name);
    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println("Bean BeanNameAware: beanName = " + beanName + ", name = " + name);
    }
}
