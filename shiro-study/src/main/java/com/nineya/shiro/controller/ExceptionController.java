package com.nineya.shiro.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 */
@ControllerAdvice
public class ExceptionController {

    public static Subject subject;
    @ExceptionHandler
    @ResponseBody
    public String ErrorHandler(AuthorizationException e) {
        System.out.println(subject.getPrincipals());
        return "没有通过权限验证！\n" + e.getMessage();
    }
}
