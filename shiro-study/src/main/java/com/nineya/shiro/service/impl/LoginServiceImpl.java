package com.nineya.shiro.service.impl;

import com.nineya.shiro.entity.Permissions;
import com.nineya.shiro.entity.Role;
import com.nineya.shiro.entity.User;
import com.nineya.shiro.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 */
@Service
public class LoginServiceImpl implements LoginService {
    private final Map<String, User> users = new HashMap<>();

    public LoginServiceImpl() {
        Permissions permissions1 = new Permissions(1, "create");
        Permissions permissions2 = new Permissions(2, "delete");
        Permissions permissions3 = new Permissions(3, "select");
        Role role1 = new Role(1, "read", new HashSet<Permissions>(){{add(permissions3);}});
        Role role2 = new Role(1, "write", new HashSet<Permissions>(){{add(permissions1);add(permissions2);}});
        users.put("observe", new User(1, "observe", "123456", Collections.singleton(role1)));
        users.put("admin", new User(1, "admin", "123456", Collections.singleton(role2)));
        users.put("user", new User(1, "user", "123456", new HashSet<Role>(){{add(role1); add(role2);}}));
    }

    @Override
    public User getUserByName(String name) {
        return users.get(name);
    }
}
