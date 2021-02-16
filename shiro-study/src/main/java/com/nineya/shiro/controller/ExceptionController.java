package com.nineya.shiro.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 * 对未通过权限认证的部分异常进行异常处理
 */
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    @ResponseBody
    public String ErrorHandler(AuthorizationException e) {
        return "没有通过权限验证！\n" + e.getMessage();
    }
}
