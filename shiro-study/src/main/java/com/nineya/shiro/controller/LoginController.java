package com.nineya.shiro.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.nineya.shiro.entity.User;
import com.nineya.shiro.service.LoginService;
import com.nineya.shiro.util.UserTokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 */
@RestController
public class LoginController {
    @Resource
    private LoginService loginService;
    @Resource
    private UserTokenUtil tokenUtil;

    @GetMapping("/login")
    public String login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return "请输入用户名和密码！";
        }
        User user = loginService.getUserByName(userName);
        if (!user.getPassword().equals(password)) {
            return "密码不正确！";
        }
        return tokenUtil.createToken(userName);
    }

//    @GetMapping("/login")
//    public String login(User user) {
//        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
//            return "请输入用户名和密码！";
//        }
//        //用户认证信息
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
//                user.getUserName(),
//                user.getPassword()
//        );
//        try {
//            //进行验证，这里可以捕获异常，然后返回对应信息
//            subject.login(usernamePasswordToken);
////            subject.checkRole("admin");
////            subject.checkPermissions("query", "add");
//        } catch (UnknownAccountException e) {
//            return "用户名不存在！";
//        } catch (AuthenticationException  e) {
//            return "账号或密码错误！";
//        } catch (AuthorizationException e) {
//            return "没有权限";
//        }
//        return "login success";
//    }

    @RequiresRoles({"read", "write"})
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequiresPermissions("select")
    @GetMapping("/select")
    public String select() {
        return "select";
    }

    @RequiresPermissions("user:create")
    @GetMapping("/create")
    public String create() {
        return "create";
    }
}
