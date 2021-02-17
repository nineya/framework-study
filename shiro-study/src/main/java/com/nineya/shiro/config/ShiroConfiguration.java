package com.nineya.shiro.config;

import com.nineya.shiro.entity.LoginType;
import com.nineya.shiro.filter.TokenFilter;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 * 配置类
 */
@Configuration
public class ShiroConfiguration {

    /**
     * 配置代理，没有配置将会导致注解不生效
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * 配置代理，没有配置将会导致注解不生效
     *
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 将自己的验证方式加入容器
     *
     * @return
     */
    @Bean
    public Realm studyRealm() {
        StudyRealm studyRealm = new StudyRealm();
        studyRealm.setName(LoginType.USER.name());
        return studyRealm;
    }

    /**
     * 将自己的验证方式加入容器
     *
     * @return
     */
    @Bean
    public Realm manageRealm() {
        ManageRealm manageRealm = new ManageRealm();
        manageRealm.setName(LoginType.MANAGE.name());
        return manageRealm;
    }


    /**
     * 不应该将过滤器的实现注册为bean，否则会导致Filter过滤器顺序混乱，导致抛出异常
     * 如果一定要注册为 Bean，可以使用 Order 指定优先级，还未尝试过
     *
     * @return
     */
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     *
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(List<Realm> realms) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager(realms));
        Map<String, String> map = new HashMap<>();
        //登出
        map.put("/logout", "logout");
        // 使用我们自己创建的jwt过滤器名称
        map.put("/**", "jwt");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/select");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setFilters(new HashMap<String, Filter>() {{
            put("jwt", tokenFilter());
        }});
        return shiroFilterFactoryBean;
    }

    /**
     * 权限管理，配置主要是Realm的管理认证，同时可以配置缓存管理等
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(List<Realm> realms) {
        DefaultWebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        webSecurityManager.setAuthenticator(modularRealmAuthenticator());
        webSecurityManager.setAuthorizer(modularRealmAuthorizer());
        //realm管理，必须在两个modular之后，因为会对这两个对象进行设值
        webSecurityManager.setRealms(realms);
        return webSecurityManager;
    }

    /**
     * 针对多realm，用于认证阶段
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        //自己重写的ModularRealmAuthenticator
        StudyModularRealmAuthenticator modularRealmAuthenticator = new StudyModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     * 针对多realm，用于授权阶段
     * @return
     */
    @Bean
    public ModularRealmAuthorizer modularRealmAuthorizer() {
        return new StudyModularRealmAuthorizer();
    }
}
