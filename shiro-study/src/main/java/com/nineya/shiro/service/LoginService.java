package com.nineya.shiro.service;

import com.nineya.shiro.entity.User;

/**
 * @author 殇雪话诀别
 * 2021/2/15
 */
public interface LoginService {

    /**
     * 通过用户名取得用户
     *
     * @param name 用户名
     * @return
     */
    User getUserByName(String name);
}
