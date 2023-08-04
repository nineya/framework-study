package com.nineya.spring.entity;

import lombok.Data;

@Data
public class BeanProcess {
    private String name;

    public void init() {
        System.out.println("Bean InitMethodName");
    }

    public void destroy() {
        System.out.println("Bean DestroyMethodName");
    }

}
