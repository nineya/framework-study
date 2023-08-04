package com.nineya.spring.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BeanProcess {
    private String name;

    public void init() {
        System.out.println("Bean InitMethodName");
    }

    public void destroy() {
        System.out.println("Bean DestroyMethodName");
    }

}
