package com.nineya.shiro.entity;

import org.apache.shiro.authc.BearerToken;

/**
 * 继承实现一个自定义的带有登录类型的Token
 * @author 殇雪话诀别
 * 2021/2/17
 */
public class JwtToken extends BearerToken {
    private final LoginType loginType;

    public JwtToken(LoginType loginType, String token, String host) {
        super(token, host);
        this.loginType = loginType;
    }

    public LoginType getLoginType() {
        return loginType;
    }
}
