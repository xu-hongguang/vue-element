package com.xhg.studyelement.shiro.dao.impl;

import com.xhg.studyelement.shiro.dao.IUserDAO;
import com.xhg.studyelement.shiro.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements IUserDAO {

    private final JdbcOperations template;

    @Autowired
    public UserDAOImpl(JdbcOperations template) {
        this.template = template;
    }

    @Override
    public User getUserByUsername(String username) {
        try {
            return template.queryForObject("select id,username,password,status,create_date from `user` where username = ? ", (rs, rowNum) -> {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setId(rs.getLong("id"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getString("status"));
                user.setCreateDate(rs.getDate("create_date"));
                return user;
            }, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }
}
