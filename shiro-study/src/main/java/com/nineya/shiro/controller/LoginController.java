package com.nineya.shiro.controller;

import com.nineya.shiro.entity.User;
import com.nineya.shiro.service.LoginService;
import com.nineya.shiro.util.UserTokenUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 使用 jwt 进行登录时此处逻辑将有些不同
     * 如果没有使用Token，用户将在此方法中通过 subject.login(usernamePasswordToken) 进行登录。
     * 使用 jwt 时，将不再使用 session 存储登录状态，subject.login(usernamePasswordToken) 逻辑将在 Filter 解析 token 时进行，并且
     * 每次请求都需要进行 token 解析和登录操作。
     * 也就是说认证、授权两个步骤，原本只要登录时进行认证，每次请求进行授权，使用 jwt 后每次请求都需要记性jwt解析、认证和授权三个步骤。
     * @param userName 用户名
     * @param password 密码
     * @return
     */
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

    // 这是没有使用 jwt 时，基于 session 的实现方式
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

    /**
     * 允许角色为 read 且为 write 用户访问
     * @return
     */
    @RequiresRoles({"read", "write"})
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    /**
     * 允许拥有 select 权限的用户访问
     * @return
     */
    @RequiresPermissions("select")
    @GetMapping("/select")
    public String select() {
        return "select";
    }

    /**
     * 允许拥有 create 权限的用户访问
     * @return
     */
    @RequiresPermissions("create")
    @GetMapping("/create")
    public String create() {
        return "create";
    }
}
