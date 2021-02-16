package com.nineya.shiro.config;

import com.nineya.shiro.entity.Permissions;
import com.nineya.shiro.entity.Role;
import com.nineya.shiro.entity.User;
import com.nineya.shiro.service.LoginService;
import com.nineya.shiro.util.UserTokenUtil;
import org.apache.catalina.realm.AuthenticatedUserRealm;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * 自定义 realm
 *
 * @author 殇雪话诀别
 * 2021/2/15
 */
public class StudyRealm extends AuthorizingRealm {
    @Resource
    private LoginService loginService;
    @Resource
    private UserTokenUtil tokenUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof BearerToken;
    }

    /**
     * 授权，在认证之后执行
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = (String) principals.getPrimaryPrincipal();
        String name = tokenUtil.getUserName(token);
        User user = loginService.getUserByName(name);
        // 添加角色和权限
        SimpleAuthorizationInfo simpleAuthenticationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            // 添加角色
            simpleAuthenticationInfo.addRole(role.getRoleName());
            // 添加权限
            simpleAuthenticationInfo.addStringPermissions(role.getPermissions().stream()
                    .map(Permissions::getPermissionsName).collect(Collectors.toSet()));
        }
        return simpleAuthenticationInfo;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        if (StringUtils.isEmpty(token.getPrincipal())) {
            return null;
        }
        String name = tokenUtil.getUserName((String) token.getPrincipal());
        User user = loginService.getUserByName(name);
        if (user == null) {
            return null;
        }
        // 第一个参数是主体，将会在授权时封装成PrincipalCollection.getPrimaryPrincipal()进行使用，所以必须将jwt内容传回
        // 第二个参数是认证信息，即密码，为后面验证可以通过，需要和token中的内容一样
        // 第三个参数是领域名称
        return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), user.getUserName());
    }
}
