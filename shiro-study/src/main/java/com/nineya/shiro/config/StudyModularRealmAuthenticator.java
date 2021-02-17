package com.nineya.shiro.config;

import com.nineya.shiro.entity.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * @author 殇雪话诀别
 * 2021/2/17
 */
public class StudyModularRealmAuthenticator extends ModularRealmAuthenticator {
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 判断 Realm 是否为空
        assertRealmsConfigured();
        Collection<Realm> realms = getRealms();
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String loginType = jwtToken.getLoginType().name();
        for (Realm realm : realms) {
            if (realm.getName().equals(loginType)) {
                return doSingleRealmAuthentication(realm, authenticationToken);
            }
        }
        return null;
    }
}
