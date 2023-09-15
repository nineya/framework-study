package com.nineya.spring.bean.cycle_plus.entity;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Lazy
@Component
public class ThreeBean {

    @Resource
    private OneBean oneBean;

    public ThreeBean() {
        System.out.println("ThreeBean：实例化");
    }

    public OneBean getOneBean() {
        return oneBean;
    }

    public void setOneBean(OneBean oneBean) {
        System.out.println("setOneBean：" + oneBean);
        this.oneBean = oneBean;
    }
}
