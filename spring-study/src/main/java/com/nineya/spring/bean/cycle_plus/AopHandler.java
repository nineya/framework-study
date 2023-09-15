package com.nineya.spring.bean.cycle_plus;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class AopHandler {

    @Pointcut("execution(* com.nineya.spring.bean.cycle_plus.entity.OneBean.get*(..))")
    private void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        System.out.printf("before %s: 被执行\n", joinPoint.getSignature().getName());
    }
}
