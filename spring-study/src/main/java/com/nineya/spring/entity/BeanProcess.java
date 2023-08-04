package com.nineya.spring.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Data
@Component
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
