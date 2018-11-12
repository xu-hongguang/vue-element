package com.xhg.studyelement.shiro.service.impl;

import com.xhg.studyelement.shiro.dao.IUserDAO;
import com.xhg.studyelement.shiro.domain.User;
import com.xhg.studyelement.shiro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private IUserDAO userDAO;

    @Autowired
    public UserServiceImpl(IUserDAO userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public User getByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }
}
