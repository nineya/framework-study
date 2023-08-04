package com.nineya.spring.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Getter
@Setter
@Component("test")
public class BeanProcess {
    private String name;

    @PostConstruct
    public void init() {
        System.out.println("Bean InitMethodName");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("Bean DestroyMethodName");
    }

}
