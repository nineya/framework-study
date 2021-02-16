package com.nineya.shiro.entity;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 * 权限
 */
public class Permissions {
    private long id;
    private String permissionsName;

    public Permissions() {
    }

    public Permissions(long id, String permissionsName) {
        this.id = id;
        this.permissionsName = permissionsName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Permissions{");
        sb.append("id=").append(id);
        sb.append(", permissionsName='").append(permissionsName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
