package com.nineya.shiro.entity;

import java.util.Set;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 * 管理员
 */
public class Manage {
    private long mid;
    private String password;
    /**
     * 用户对应的角色
     */
    private Set<Role> roles;

    public Manage() {
    }

    public Manage(long uid, String password, Set<Role> roles) {
        this.mid = uid;
        this.password = password;
        this.roles = roles;
    }

    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
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
        final StringBuilder sb = new StringBuilder("Manage{");
        sb.append("mid=").append(mid);
        sb.append(", password='").append(password).append('\'');
        sb.append(", roles=").append(roles);
        sb.append('}');
        return sb.toString();
    }
}
