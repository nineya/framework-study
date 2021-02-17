package com.nineya.shiro.filter;

import com.nineya.shiro.controller.ExceptionController;
import com.nineya.shiro.entity.JwtToken;
import com.nineya.shiro.entity.LoginType;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 */
public class TokenFilter extends BasicHttpAuthenticationFilter {

    private static final String MANAGE_AUTHORIZATION = "Manage-Authorization";
    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        return req.getHeader(AUTHORIZATION_HEADER) != null || req.getHeader(MANAGE_AUTHORIZATION) != null;
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //判断请求的请求头是否带上 "Token"
        if (isLoginAttempt(request, response)) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                return executeLogin(request, response);
            } catch (Exception e) {
                //token 错误
                e.printStackTrace();
                return false;
            }
        }
        //如果请求头不存在 Token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        LoginType loginType = null;
        String token = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if (token != null) {
            loginType = LoginType.USER;
        } else {
            token = httpServletRequest.getHeader(MANAGE_AUTHORIZATION);
            if (token != null) {
                loginType = LoginType.MANAGE;
            }
        }
        JwtToken jwtToken = new JwtToken(loginType, token, request.getRemoteAddr());
        getSubject(request, response).login(jwtToken);
        return true;
    }
}
