package com.nineya.shiro.config;

import com.nineya.shiro.entity.*;
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
 * 自定义管理员的 realm
 *
 * @author 殇雪话诀别
 * 2021/2/15
 */
public class ManageRealm extends AuthorizingRealm {
    @Resource
    private LoginService loginService;
    @Resource
    private UserTokenUtil tokenUtil;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权，在认证之后执行
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println(this.getClass().getName());
        String token = (String) principals.getPrimaryPrincipal();
        String name = tokenUtil.getUserName(token);
        Manage manage = loginService.getManageById(Long.parseLong(name));
        if (manage == null) {
            return null;
        }
        // 添加角色和权限
        SimpleAuthorizationInfo simpleAuthenticationInfo = new SimpleAuthorizationInfo();
        for (Role role : manage.getRoles()) {
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
        System.out.println(this.getClass().getName());
        if (StringUtils.isEmpty(token.getPrincipal())) {
            return null;
        }
        String name = tokenUtil.getUserName((String) token.getPrincipal());
        Manage manage = loginService.getManageById(Long.parseLong(name));
        if (manage == null) {
            return null;
        }
        // 第一个参数是主体，将会在授权时封装成PrincipalCollection.getPrimaryPrincipal()进行使用，所以必须将jwt内容传回
        // 第二个参数是认证信息，即密码，为后面验证可以通过，需要和token中的内容一样
        // 第三个参数是领域名称
        return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());
    }
}
