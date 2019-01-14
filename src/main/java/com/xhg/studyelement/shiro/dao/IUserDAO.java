package com.xhg.studyelement.shiro.dao;

import com.xhg.studyelement.shiro.domain.User;

import java.util.List;
import java.util.Map;

public interface IUserDAO {
    /**
     * 通过用户名查找用户对象
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    List<User> findAllUsers(Map<String,Object> map);

    int countUser(Map<String,Object> map);
}
