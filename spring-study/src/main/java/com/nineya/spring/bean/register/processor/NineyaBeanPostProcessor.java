package com.nineya.spring.bean.register.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class NineyaBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("后置处理器BeanPostProcessor: postProcessAfterInitialization(" + beanName + ") ：" + bean);
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("后置处理器BeanPostProcessor: postProcessBeforeInitialization(" + beanName + ") ：" + bean);
        return bean;
    }
}
