package com.xhg.studyelement.shiro.dao.impl;

import com.xhg.studyelement.shiro.dao.IUserDAO;
import com.xhg.studyelement.shiro.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
            return template.queryForObject("select id,username,password,status,create_date,sex,email,phone from `user` where username = ? ", (rs, rowNum) -> {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setId(rs.getLong("id"));
                user.setPassword(rs.getString("password"));
                user.setStatus(rs.getString("status"));
                user.setCreateDate(rs.getDate("create_date"));
                user.setSex(rs.getString("sex"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                return user;
            }, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> findAllUsers(Map<String, Object> map) {
        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return template.query("SELECT id,username,password,status,create_date,sex,email,phone FROM `user` ORDER BY id limit ?,?", rowMapper,
                map.get("offset"), map.get("limit"));
    }

    @Override
    public int countUser(Map<String, Object> map) {
        int count =  template.queryForObject("select count(1) from `user` order by id limit ?,?", Integer.class, map.get("offset"), map.get("limit"));
        return count;
    }
}
