package com.nineya.springboot.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class MvcMain {

    /**
     * 修改DispatcherServlet默认配置
     */
    @Bean
    public ServletRegistrationBean<?> dispatcherRegistration(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean<?> registration = new ServletRegistrationBean(dispatcherServlet);
        registration.getUrlMappings().clear();
        registration.addUrlMappings("*.html");
        return registration;
    }

    public static void main(String[] args) {
        SpringApplication.run(MvcMain.class, args);
    }
}
