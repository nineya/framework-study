package com.nineya.spring.bean.cycle_thread.entity;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Lazy
@Component
public class OneBean {
    @Resource
    private TwoBean twoBean;

    public OneBean() {
        System.out.println("OneBean：实例化");
    }

    public TwoBean getTwoBean() {
        return twoBean;
    }

    public void setTwoBean(TwoBean twoBean) {
        System.out.println("setTwoBean：" + twoBean);
        this.twoBean = twoBean;
    }
}
