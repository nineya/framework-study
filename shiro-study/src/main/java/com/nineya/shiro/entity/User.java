package com.nineya.shiro.entity;

import java.util.Set;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 * 用户，包含角色集合
 */
public class User {
    private long uid;
    private String userName;
    private String password;
    /**
     * 用户对应的角色
     */
    private Set<Role> roles;

    public User() {
    }

    public User(long uid, String userName, String password, Set<Role> roles) {
        this.uid = uid;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("uid=").append(uid);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
