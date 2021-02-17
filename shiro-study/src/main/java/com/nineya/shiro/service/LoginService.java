package com.nineya.shiro.service;

import com.nineya.shiro.entity.Manage;
import com.nineya.shiro.entity.User;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 * 用户登录服务接口
 */
public interface LoginService {

    /**
     * 通过用户名取得用户
     *
     * @param name 用户名
     * @return
     */
    User getUserByName(String name);

    /**
     * 通过管理员id获取管理员信息
     * @param mid
     * @return
     */
    Manage getManageById(long mid);
}
