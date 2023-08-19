package com.nineya.spring.aop;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.nineya.spring.aop")
@EnableAspectJAutoProxy
public class AopConfiguration {
    @Bean(value = "test")
    public BeanObject beanObject() {
        BeanObject beanObject = new BeanObject();
        beanObject.setName("test");
        return beanObject;
    }
}
