package com.nineya.spring.bean.register.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Getter
@Setter
@ToString
@Component("test")
public class BeanProcess {
    private String name;
    
    @PostConstruct
    public void initMethod() {
        System.out.println("Bean InitMethodName: name = " + name);
    }

    @PreDestroy
    public void destroyMethod() {
        System.out.println("Bean DestroyMethodName: name = " + name);
    }
}
