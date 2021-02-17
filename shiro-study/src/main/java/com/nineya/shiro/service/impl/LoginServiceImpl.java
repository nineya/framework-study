package com.nineya.shiro.service.impl;

import com.nineya.shiro.entity.Manage;
import com.nineya.shiro.entity.Permissions;
import com.nineya.shiro.entity.Role;
import com.nineya.shiro.entity.User;
import com.nineya.shiro.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 * 用户登录服务，使用Map<用户名, 用户信息> 的格式存储用户信息进行查询
 * 在实际使用中将此处修改为连接数据库查询即可
 */
@Service
public class LoginServiceImpl implements LoginService {
    private final Map<String, User> users = new HashMap<>();
    private final Map<Long, Manage> manages = new HashMap<>();

    public LoginServiceImpl() {
        // 定义三个权限
        Permissions permissions1 = new Permissions(1, "create");
        Permissions permissions2 = new Permissions(2, "delete");
        Permissions permissions3 = new Permissions(3, "select");
        // 定义两个角色
        Role role1 = new Role(1, "read", new HashSet<Permissions>(){{add(permissions3);}});
        Role role2 = new Role(1, "write", new HashSet<Permissions>(){{add(permissions1);add(permissions2);}});
        // 定义三个用户分别对应两个角色
        users.put("observe", new User(1, "observe", "123456", Collections.singleton(role1)));
        users.put("admin", new User(1, "admin", "123456", Collections.singleton(role2)));
        users.put("user", new User(1, "user", "123456", new HashSet<Role>(){{add(role1); add(role2);}}));

        // 定义一个 manage角色，拥有所有权限
        Permissions permissions4 = new Permissions(4, "update");
        Role role3 = new Role(1, "manage", new HashSet<Permissions>(){{add(permissions1);add(permissions2);add(permissions3);add(permissions4);}});
        manages.put(1L, new Manage(1, "123456", Collections.singleton(role3)));
    }

    @Override
    public User getUserByName(String name) {
        return users.get(name);
    }

    @Override
    public Manage getManageById(long mid) {
        return manages.get(mid);
    }
}
